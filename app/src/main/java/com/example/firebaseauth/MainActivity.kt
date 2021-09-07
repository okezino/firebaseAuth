package com.example.firebaseauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    lateinit var button: Button
    lateinit var verify: TextView
    lateinit var authAdapter: AuthAdapter
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.button_logout)
        verify = findViewById(R.id.verification)
        authAdapter = AuthAdapter()
        recyclerView = findViewById(R.id.usersRecyclerView)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = authAdapter


        if (App.firebaseAuthInstance.currentUser == null) {
            startActivity(Intent(this, Login::class.java))
            finish()
        }

        if (App.firebaseAuthInstance.currentUser!!.isEmailVerified) verify.isVisible = false

        button.setOnClickListener {
            App.firebaseAuthInstance.signOut()
            startActivity(Intent(this, Login::class.java))
            finish()
        }

    }
}