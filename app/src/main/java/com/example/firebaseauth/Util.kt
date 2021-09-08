package com.example.firebaseauth

import android.app.Activity
import androidx.appcompat.app.AlertDialog

fun emailValidator(email: String) : Boolean{

    val regex = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
    return email.matches(regex)
}

