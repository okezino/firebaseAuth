package com.example.firebaseauth

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var loginButton: Button
    lateinit var sendLink: Button


    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        email = findViewById(R.id.login_email)
        password = findViewById(R.id.login_password)
        loginButton = findViewById(R.id.login_btn)
        sendLink = findViewById(R.id.password_link)


        loginButton.setOnClickListener { view ->

            if(emailValidator(email.text.toString()) && password.text.toString().length > 5){
                App.firebaseAuthInstance.signInWithEmailAndPassword(
                    email.text.toString(),
                    password.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        startActivity(Intent(this, MainActivity::class.java))
                    } else {
                        displayMessage(view, it.exception.toString())
                        startActivity(Intent(this, Registration::class.java))

                    }
                }

            }else {
                displayMessage(view,"Invalid email or Password is less than 6 digit" )

            }

        }
        fun sendResetPasswordLink(view : View,email: String) {
            App.firebaseAuthInstance.sendPasswordResetEmail(email.trim()).addOnSuccessListener {
                displayMessage(view,"Link has been sent to your email")
            }.addOnFailureListener {
                Throwable(it)
            }
        }
        sendLink.setOnClickListener {view ->
            val edit = EditText(this)
            val passwordResetAlertDialog: AlertDialog.Builder = AlertDialog.Builder(this).apply {

                setTitle("Reset Password")
                setMessage("Enter your email to receive password Link")
                setView(edit)
                setPositiveButton(
                    "Yes"
                ) { p0, p1 ->
                    val email = edit.text.toString()
                    sendResetPasswordLink(view, email)
                }
                setNegativeButton("No") { p0, p1 ->
                    p0.dismiss()
                }
            }
            passwordResetAlertDialog.create().show()
        }
    }

    @SuppressLint("ResourceAsColor")
    fun displayMessage(view: View, message : String){
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(R.color.purple_500).show()
    }
}