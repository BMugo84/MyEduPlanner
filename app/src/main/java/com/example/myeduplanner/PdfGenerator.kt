package com.example.myeduplanner

import android.content.Context
import android.content.Intent
import android.os.Build
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.webkit.WebView
import android.webkit.WebViewClient
import java.io.File

class PdfGenerator(private val context: Context) {

    interface PdfGenerationListener {
        fun onPdfGenerated(file: File)
        fun onError(error: String)
    }

    fun generatePdfFromHtml(
        htmlContent: String,
        outputFile: File,
        listener: PdfGenerationListener
    ) {
        try {
            val webView = WebView(context)
            webView.settings.javaScriptEnabled = false
            webView.settings.loadWithOverviewMode = true
            webView.settings.useWideViewPort = true

            webView.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)

                    // Create PDF after HTML is fully loaded
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        createPdfFromWebView(webView, outputFile, listener)
                    } else {
                        listener.onError("PDF generation requires Android 4.4 or higher")
                    }
                }
            }

            // Load HTML content
            webView.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null)

        } catch (e: Exception) {
            listener.onError("Error: ${e.message}")
        }
    }

    private fun createPdfFromWebView(
        webView: WebView,
        outputFile: File,
        listener: PdfGenerationListener
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val printAdapter: PrintDocumentAdapter = webView.createPrintDocumentAdapter("document")

            val attributes = PrintAttributes.Builder()
                .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
                .setResolution(PrintAttributes.Resolution("pdf", "pdf", 600, 600))
                .setMinMargins(PrintAttributes.Margins.NO_MARGINS)
                .build()

            try {
                printAdapter.onLayout(
                    null,
                    attributes,
                    null,
                    object : PrintDocumentAdapter.LayoutResultCallback() {
                        override fun onLayoutFinished(info: android.print.PrintDocumentInfo, changed: Boolean) {
                            printAdapter.onWrite(
                                arrayOf(android.print.PageRange.ALL_PAGES),
                                android.os.ParcelFileDescriptor.open(
                                    outputFile,
                                    android.os.ParcelFileDescriptor.MODE_READ_WRITE or
                                            android.os.ParcelFileDescriptor.MODE_CREATE or
                                            android.os.ParcelFileDescriptor.MODE_TRUNCATE
                                ),
                                null,
                                object : PrintDocumentAdapter.WriteResultCallback() {
                                    override fun onWriteFinished(pages: Array<out android.print.PageRange>) {
                                        super.onWriteFinished(pages)

                                        // Notify media scanner
                                        val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
                                        intent.data = android.net.Uri.fromFile(outputFile)
                                        context.sendBroadcast(intent)

                                        listener.onPdfGenerated(outputFile)
                                    }

                                    override fun onWriteFailed(error: CharSequence?) {
                                        super.onWriteFailed(error)
                                        listener.onError("Failed to write PDF: $error")
                                    }
                                }
                            )
                        }

                        override fun onLayoutFailed(error: CharSequence?) {
                            listener.onError("Failed to layout PDF: $error")
                        }
                    },
                    null
                )
            } catch (e: Exception) {
                listener.onError("Error creating PDF: ${e.message}")
            }
        }
    }
}