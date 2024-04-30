package com.aamg.undercontrol.utils

import android.app.AlertDialog
import android.content.Context

fun showErrorDialog(context: Context, message: String) {
    val dialog = AlertDialog.Builder(context)
        .setTitle("Error")
        .setMessage(message)
        .setPositiveButton("OK", null)
        .create()

    dialog.show()
}