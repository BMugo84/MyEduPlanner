package com.example.myeduplanner

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
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
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            listener.onError("PDF generation requires Android 4.4 or higher")
            return
        }

        try {
            val webView = WebView(context.applicationContext)
            webView.settings.apply {
                javaScriptEnabled = false
                loadWithOverviewMode = true
                useWideViewPort = true
            }

            webView.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)

                    Handler(Looper.getMainLooper()).postDelayed({
                        view?.let { createPdfFromWebView(it, outputFile, listener) }
                    }, 500)
                }

                override fun onReceivedError(
                    view: WebView?,
                    errorCode: Int,
                    description: String?,
                    failingUrl: String?
                ) {
                    super.onReceivedError(view, errorCode, description, failingUrl)
                    listener.onError("WebView error: $description")
                }
            }

            webView.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null)

        } catch (e: Exception) {
            listener.onError("Error loading HTML: ${e.message}")
        }
    }

    private fun createPdfFromWebView(
        webView: WebView,
        outputFile: File,
        listener: PdfGenerationListener
    ) {
        try {
            val printAdapter = webView.createPrintDocumentAdapter(outputFile.nameWithoutExtension)

            val attributes = PrintAttributes.Builder()
                .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
                .setResolution(PrintAttributes.Resolution("pdf", "pdf", 600, 600))
                .setMinMargins(PrintAttributes.Margins.NO_MARGINS)
                .build()

            val fileDescriptor = android.os.ParcelFileDescriptor.open(
                outputFile,
                android.os.ParcelFileDescriptor.MODE_READ_WRITE or
                        android.os.ParcelFileDescriptor.MODE_CREATE or
                        android.os.ParcelFileDescriptor.MODE_TRUNCATE
            )

            // Use reflection to create callback instances to bypass stub restrictions
            val layoutCallback = createLayoutCallback(printAdapter, fileDescriptor, outputFile, listener)

            printAdapter.onLayout(null, attributes, null, layoutCallback, null)

        } catch (e: Exception) {
            listener.onError("Error creating PDF: ${e.message}")
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun createLayoutCallback(
        printAdapter: PrintDocumentAdapter,
        fileDescriptor: android.os.ParcelFileDescriptor,
        outputFile: File,
        listener: PdfGenerationListener
    ): PrintDocumentAdapter.LayoutResultCallback {

        return object : PrintDocumentAdapter.LayoutResultCallback() {
            override fun onLayoutFinished(info: android.print.PrintDocumentInfo, changed: Boolean) {
                val writeCallback = createWriteCallback(fileDescriptor, outputFile, listener)
                printAdapter.onWrite(
                    arrayOf(android.print.PageRange.ALL_PAGES),
                    fileDescriptor,
                    null,
                    writeCallback
                )
            }

            override fun onLayoutFailed(error: CharSequence?) {
                closeFileDescriptor(fileDescriptor)
                listener.onError("Failed to layout PDF: $error")
            }

            override fun onLayoutCancelled() {
                closeFileDescriptor(fileDescriptor)
                listener.onError("PDF layout was cancelled")
            }
        }
    }

    private fun createWriteCallback(
        fileDescriptor: android.os.ParcelFileDescriptor,
        outputFile: File,
        listener: PdfGenerationListener
    ): PrintDocumentAdapter.WriteResultCallback {

        return object : PrintDocumentAdapter.WriteResultCallback() {
            override fun onWriteFinished(pages: Array<out android.print.PageRange>) {
                closeFileDescriptor(fileDescriptor)
                notifyMediaScanner(outputFile)
                listener.onPdfGenerated(outputFile)
            }

            override fun onWriteFailed(error: CharSequence?) {
                closeFileDescriptor(fileDescriptor)
                listener.onError("Failed to write PDF: $error")
            }

            override fun onWriteCancelled() {
                closeFileDescriptor(fileDescriptor)
                listener.onError("PDF write was cancelled")
            }
        }
    }

    private fun closeFileDescriptor(fileDescriptor: android.os.ParcelFileDescriptor) {
        try {
            fileDescriptor.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun notifyMediaScanner(file: File) {
        try {
            val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            intent.data = android.net.Uri.fromFile(file)
            context.sendBroadcast(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}