package com.example.firebaseauth

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class App : Application() {



    override fun onCreate() {
        super.onCreate()
        instance = this

    }

    companion object{
        val firebaseAuthInstance by lazy {
            FirebaseAuth.getInstance()
        }
        lateinit var instance : App

        private val fstore by lazy {
            FirebaseFirestore.getInstance()
        }

        private val userId by lazy {
            firebaseAuthInstance.currentUser?.uid
        }

       private val documentReference by lazy {
            fstore.collection("users").document(userId!!)
       }




    }
}