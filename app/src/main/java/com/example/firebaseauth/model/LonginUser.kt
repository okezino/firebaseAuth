package com.example.firebaseauth.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class LoginUser(var email : String? = null ,var name:String? = null, var  phone : String? = null) :Parcelable