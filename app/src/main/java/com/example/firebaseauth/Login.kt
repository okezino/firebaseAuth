package com.example.firebaseauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    lateinit var email : EditText
    lateinit var password : EditText
    lateinit var loginButton : Button
    lateinit var firebaseAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        firebaseAuth = FirebaseAuth.getInstance()

        email = findViewById(R.id.login_email)
        password = findViewById(R.id.login_password)
        loginButton = findViewById(R.id.login_btn)

        loginButton.setOnClickListener {
           App.firebaseAuthInstance.signInWithEmailAndPassword(email.text.toString(), password.text.toString()) .addOnCompleteListener {
              if(it.isSuccessful ){
                  startActivity(Intent(this, MainActivity::class.java))
              }else{
                  startActivity(Intent(this, Registration::class.java))
              }
           }
        }
    }
}