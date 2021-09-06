package com.example.firebaseauth

import android.app.Activity
import androidx.appcompat.app.AlertDialog

fun generateMaterialDialog(
    context: Activity, title: String, message: String
    , positiveBtnTitle: String, negativeBtnTitle: String = "",
    positiveAction: (() -> Unit)?, negativeAction: (() -> Unit)?
){
    AlertDialog.Builder(context)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(positiveBtnTitle) { dialogInterface, _ ->
            dialogInterface.dismiss()
            positiveAction?.invoke()
        }.setNegativeButton(negativeBtnTitle) { dialogInterface, _ ->
            dialogInterface.dismiss()
            negativeAction?.invoke()
        }.setCancelable(true)
        .show()
}