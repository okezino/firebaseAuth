package com.example.firebaseauth

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseauth.model.LoginUser
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    lateinit var button: Button
    lateinit var verify: TextView
    lateinit var authAdapter: AuthAdapter
    lateinit var recyclerView: RecyclerView

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.button_logout)
        verify = findViewById(R.id.verification)
        authAdapter = AuthAdapter(mutableListOf())
        recyclerView = findViewById(R.id.usersRecyclerView)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = authAdapter

        verify.setOnClickListener {
            App.firebaseAuthInstance.currentUser?.sendEmailVerification()
            Snackbar.make(it, "Kindly check your email for verification link", Snackbar.LENGTH_SHORT)
                .setBackgroundTint(R.color.purple_500).show()
        }






        if (App.firebaseAuthInstance.currentUser == null) {
            startActivity(Intent(this, Login::class.java))
            finish()
        }

        App.firebaseAuthInstance.currentUser?.let {
            if (it.isEmailVerified) {
                verify.isVisible = false

                App.documentRef.addSnapshotListener { value, error ->
                    var userlist = mutableListOf<LoginUser>()

                    value?.documents?.forEach { userId ->

                        App.documentRef.document(userId.id).get().addOnSuccessListener { login ->
                            val user = login.data
                            if(user != null){
                                val track = login.toObject(LoginUser::class.java)
                                userlist.add(track!!)
                                Log.d("MainActivity", track.toString())
                                authAdapter = AuthAdapter(userlist)
                                recyclerView.adapter = authAdapter


                            }
                        }

                    }
                }
            }

        }



        button.setOnClickListener {
            App.firebaseAuthInstance.signOut()
            startActivity(Intent(this, Login::class.java))
            finish()
        }

    }
}