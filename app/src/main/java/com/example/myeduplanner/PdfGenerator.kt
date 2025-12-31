package com.example.myeduplanner

import android.content.Context
import android.content.Intent
import java.io.File
import java.io.FileWriter

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
            // Write HTML content to file
            FileWriter(outputFile).use { writer ->
                writer.write(htmlContent)
            }

            // Notify media scanner
            val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            intent.data = android.net.Uri.fromFile(outputFile)
            context.sendBroadcast(intent)

            // Notify success
            listener.onPdfGenerated(outputFile)

        } catch (e: Exception) {
            e.printStackTrace()
            listener.onError("Error saving file: ${e.message}")
        }
    }
}