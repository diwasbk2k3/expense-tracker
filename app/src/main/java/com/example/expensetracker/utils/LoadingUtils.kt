package com.example.expensetracker.utils

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.example.expensetracker.R

class LoadingUtils(val activity: Activity) {
    lateinit var alertDialog: AlertDialog

    fun show(){
        val builder = AlertDialog.Builder(activity)
        val designView = activity.layoutInflater.inflate(R.layout.loading, null)
        builder.setTitle("Please wait")
        builder.setView(designView)
        builder.setCancelable(false)
        alertDialog = builder.create()
        alertDialog.show()
    }

    fun dismiss(){
        alertDialog.dismiss()
    }
}