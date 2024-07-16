package com.aamg.undercontrol.ui.view.fragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.text.SimpleDateFormat
import java.util.Calendar

class DatePickerFragment(
    private val date: String,
    val listener: (Int, Int, Int) -> Unit,
) : DialogFragment(), DatePickerDialog.OnDateSetListener {
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        listener(dayOfMonth, month, year)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val maxDate = c.timeInMillis
        if (date.isNotEmpty()) {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
            val parsedDate = try {
                dateFormat.parse(date)
            } catch (e: Exception) {
                null
            }
            if (parsedDate != null) c.time = parsedDate
        }
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)
        val datePicker = DatePickerDialog(activity as Context, this, year, month, day)
        datePicker.datePicker.maxDate = maxDate
        return datePicker
    }
}