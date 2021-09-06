package com.example.firebaseauth

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var loginButton: Button
    lateinit var sendLink: Button
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        firebaseAuth = FirebaseAuth.getInstance()

        email = findViewById(R.id.login_email)
        password = findViewById(R.id.login_password)
        loginButton = findViewById(R.id.login_btn)
        sendLink = findViewById(R.id.password_link)


        loginButton.setOnClickListener {
            App.firebaseAuthInstance.signInWithEmailAndPassword(
                email.text.toString(),
                password.text.toString()
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    startActivity(Intent(this, Registration::class.java))
                }
            }
        }
        fun sendResetPasswordLink(email: String) {
            App.firebaseAuthInstance.sendPasswordResetEmail(email.trim()).addOnSuccessListener {
                Toast.makeText(this, "Link has been sent to your email", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Throwable(it)
            }
        }
        sendLink.setOnClickListener {
            val edit = EditText(this)
            val passwordResetAlertDialog: AlertDialog.Builder = AlertDialog.Builder(this).apply {

                setTitle("Reset Password")
                setMessage("Enter your email to receive password Link")
                setView(edit)
                setPositiveButton(
                    "Yes"
                ) { p0, p1 ->
                    val email = edit.text.toString()
                    sendResetPasswordLink(email)
                }
                setNegativeButton("No") { p0, p1 ->
                    p0.dismiss()
                }
            }
            passwordResetAlertDialog.create().show()
        }
    }
}