package com.example.myeduplanner

import android.content.Context
import android.content.Intent
import android.os.Build
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.print.PrintHelper
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
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                listener.onError("PDF generation requires Android 4.4 or higher")
                return
            }

            val webView = WebView(context)
            webView.settings.javaScriptEnabled = false
            webView.settings.loadWithOverviewMode = true
            webView.settings.useWideViewPort = true

            webView.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)

                    android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                        try {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                // Use print manager
                                val printManager = context.getSystemService(Context.PRINT_SERVICE) as PrintManager
                                val printAdapter = webView.createPrintDocumentAdapter(outputFile.name)
                                val attributes = PrintAttributes.Builder()
                                    .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
                                    .setResolution(PrintAttributes.Resolution("pdf", "pdf", 600, 600))
                                    .setMinMargins(PrintAttributes.Margins.NO_MARGINS)
                                    .build()

                                // Print to PDF (this will use system print dialog)
                                // For background saving, we need a workaround
                                savePdfInBackground(webView, outputFile, listener)
                            }
                        } catch (e: Exception) {
                            listener.onError("Error creating PDF: ${e.message}")
                        }
                    }, 500)
                }
            }

            webView.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null)

        } catch (e: Exception) {
            listener.onError("Error: ${e.message}")
        }
    }

    private fun savePdfInBackground(webView: WebView, outputFile: File, listener: PdfGenerationListener) {
        try {
            // Create a bitmap from the webview
            webView.measure(
                android.view.View.MeasureSpec.makeMeasureSpec(595, android.view.View.MeasureSpec.EXACTLY),
                android.view.View.MeasureSpec.makeMeasureSpec(842, android.view.View.MeasureSpec.EXACTLY)
            )
            webView.layout(0, 0, webView.measuredWidth, webView.measuredHeight)

            val bitmap = android.graphics.Bitmap.createBitmap(
                webView.measuredWidth,
                webView.measuredHeight,
                android.graphics.Bitmap.Config.ARGB_8888
            )
            val canvas = android.graphics.Canvas(bitmap)
            webView.draw(canvas)

            // Create PDF from bitmap
            val pdfDocument = android.graphics.pdf.PdfDocument()
            val pageInfo = android.graphics.pdf.PdfDocument.PageInfo.Builder(595, 842, 1).create()
            val page = pdfDocument.startPage(pageInfo)

            val pdfCanvas = page.canvas
            pdfCanvas.drawBitmap(bitmap, 0f, 0f, null)

            pdfDocument.finishPage(page)

            // Write to file
            java.io.FileOutputStream(outputFile).use { outputStream ->
                pdfDocument.writeTo(outputStream)
            }

            pdfDocument.close()
            bitmap.recycle()

            // Notify media scanner
            val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            intent.data = android.net.Uri.fromFile(outputFile)
            context.sendBroadcast(intent)

            listener.onPdfGenerated(outputFile)

        } catch (e: Exception) {
            e.printStackTrace()
            listener.onError("Error saving PDF: ${e.message}")
        }
    }
}