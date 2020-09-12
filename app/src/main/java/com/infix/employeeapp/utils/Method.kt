package com.infix.employeeapp.utils

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

object Method {

    fun showLog(msg: String) {
        Log.d("AppLogs", msg)
    }

    fun showToast(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

    fun showSnackBar(root: View, message: String, messageTypeColor: Int) {
        val snackBar: Snackbar = Snackbar.make(root, message, 5000)
        snackBar.view.setBackgroundColor(ContextCompat.getColor(root.context, messageTypeColor))
        snackBar.show()
    }

}