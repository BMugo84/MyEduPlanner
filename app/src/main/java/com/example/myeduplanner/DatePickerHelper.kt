package com.example.myeduplanner

import android.app.DatePickerDialog
import android.content.Context
import android.widget.EditText
import java.text.SimpleDateFormat
import java.util.*

class DatePickerHelper(private val context: Context) {

    private val calendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    /**
     * Show date picker and set the selected date to the EditText
     * Format: dd/MM/yyyy (e.g., 15/11/2025)
     */
    fun showDatePicker(editText: EditText) {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                // Update calendar with selected date
                calendar.set(selectedYear, selectedMonth, selectedDay)

                // Format and set the date to EditText
                val formattedDate = dateFormat.format(calendar.time)
                editText.setText(formattedDate)
            },
            year,
            month,
            day
        )

        // Show the dialog
        datePickerDialog.show()
    }

    /**
     * Show date picker with long format
     * Format: EEEE, dd MMMM yyyy (e.g., Monday, 15 November 2025)
     */
    fun showDatePickerLongFormat(editText: EditText) {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val longDateFormat = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault())

        val datePickerDialog = DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                calendar.set(selectedYear, selectedMonth, selectedDay)
                val formattedDate = longDateFormat.format(calendar.time)
                editText.setText(formattedDate)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }
}