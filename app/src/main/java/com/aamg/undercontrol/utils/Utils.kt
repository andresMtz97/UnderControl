package com.aamg.undercontrol.utils

import android.app.AlertDialog
import android.content.Context
import android.icu.text.DecimalFormat
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.aamg.undercontrol.R

fun showErrorDialog(context: Context, message: String) {
    val dialog = AlertDialog.Builder(context)
        .setTitle("Error")
        .setMessage(message)
        .setPositiveButton("OK", null)
        .create()

    dialog.show()
}

fun String.capitalize(): String = this.replaceFirstChar { it.uppercase() }

fun Double.toCurrency(): String {
    val decimalFormat = DecimalFormat("$###,###,##0.00")
    return decimalFormat.format(this)
}

fun Spinner.setAdapter(list: ArrayList<String>) {
    list.add(0, this.context.getString(R.string.option_default))
    val adapter = ArrayAdapter(this.context, android.R.layout.simple_spinner_item, list)
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    this.adapter = adapter
}