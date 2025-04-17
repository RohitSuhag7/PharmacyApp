package com.example.pharmacyapp.utils

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import java.io.File
import java.io.IOException

class PDFRendererHelper(private val file: File) {

    private var pdfRenderer: PdfRenderer? = null
    private var currentPage: PdfRenderer.Page? = null

    init {
        try {
            val fileDescriptor =
                ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
            pdfRenderer = PdfRenderer(fileDescriptor)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    // Get the total number of pages in the PDF
    fun getPageCount(): Int {
        return pdfRenderer?.pageCount ?: 0
    }

    // Get a bitmap representation of the page
    fun getPageBitmap(pageIndex: Int): Bitmap? {
        if (pageIndex >= getPageCount()) return null

        // Close the current page if it is open
        currentPage?.close()

        // Open the requested page
        currentPage = pdfRenderer?.openPage(pageIndex)

        // Create a bitmap to render the page
        val bitmap = Bitmap.createBitmap(
            currentPage?.width ?: 0,
            currentPage?.height ?: 0,
            Bitmap.Config.ARGB_8888
        )
        currentPage?.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
        return bitmap
    }

    fun close() {
        currentPage?.close()
        pdfRenderer?.close()
    }
}
