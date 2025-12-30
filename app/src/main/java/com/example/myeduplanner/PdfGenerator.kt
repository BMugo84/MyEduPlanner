package com.example.myeduplanner

import android.content.Context
import android.content.Intent
import android.os.Build
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintManager
import android.webkit.WebView
import android.webkit.WebViewClient
import java.io.File
import java.io.FileOutputStream

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
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                listener.onError("PDF generation requires Android 4.4 or higher")
                return
            }

            val webView = WebView(context)
            webView.settings.javaScriptEnabled = false

            webView.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)

                    // Small delay to ensure rendering is complete
                    android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                        try {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                createPdf(webView, outputFile, listener)
                            }
                        } catch (e: Exception) {
                            listener.onError("Error creating PDF: ${e.message}")
                        }
                    }, 300)
                }
            }

            webView.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null)

        } catch (e: Exception) {
            listener.onError("Error: ${e.message}")
        }
    }

    private fun createPdf(webView: WebView, outputFile: File, listener: PdfGenerationListener) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                val printAdapter = webView.createPrintDocumentAdapter(outputFile.name)

                // Use PrintedPdfDocument approach
                val pdfDocument = android.graphics.pdf.PdfDocument()

                // Create a page
                val pageInfo = android.graphics.pdf.PdfDocument.PageInfo.Builder(595, 842, 1).create() // A4 size
                val page = pdfDocument.startPage(pageInfo)

                // Draw the web view content
                webView.draw(page.canvas)

                pdfDocument.finishPage(page)

                // Write to file
                FileOutputStream(outputFile).use { outputStream ->
                    pdfDocument.writeTo(outputStream)
                }

                pdfDocument.close()

                // Notify media scanner
                val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
                intent.data = android.net.Uri.fromFile(outputFile)
                context.sendBroadcast(intent)

                listener.onPdfGenerated(outputFile)

            } catch (e: Exception) {
                e.printStackTrace()
                listener.onError("Error creating PDF: ${e.message}")
            }
        }
    }
}