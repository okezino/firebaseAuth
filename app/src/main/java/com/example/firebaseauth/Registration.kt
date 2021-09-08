package com.example.firebaseauth

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class Registration : AppCompatActivity() {

    lateinit var fullName: EditText
    lateinit var phone: EditText
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var regButton: Button
    lateinit var progress: ProgressBar

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)


        fullName = findViewById(R.id.registration_name)
        progress = findViewById(R.id.registration_progressBar)
        phone = findViewById(R.id.registration_phone)
        email = findViewById(R.id.registration_email)
        password = findViewById(R.id.registration_password)
        regButton = findViewById(R.id.button_reg)

        regButton.setOnClickListener {
            progress.isVisible = true
            if(emailValidator(email.text.toString()) && password.text.toString().length > 5){

                App.firebaseAuthInstance.createUserWithEmailAndPassword(
                    email.text.toString(),
                    password.text.toString()
                )
                    .addOnCompleteListener { p0 ->
                        App.firebaseAuthInstance.currentUser!!.sendEmailVerification()

                        if (p0.isSuccessful) {
                            progress.isVisible = false
                            val hash = hashMapOf(
                                "name" to fullName.text.toString(),
                                "email" to email.text.toString(),
                                "phone" to phone.text.toString(),
                            )
                            App.documentReference.set(hash).addOnCompleteListener {task->
                                if(!task.isSuccessful){
                                    Throwable(task.exception)
                                }
                            }
                            startActivity(Intent(this, Login::class.java))
                        } else {

                            Throwable(p0.exception)
                        }
                    }

            }else{

                Snackbar.make(it, "Invalid email or Password is less than 6 digit", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(R.color.purple_500).show()
                progress.isVisible = false
            }

        }
    }


}