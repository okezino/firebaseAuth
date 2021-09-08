package com.example.firebaseauth

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseauth.model.LoginUser
import com.example.firebaseauth.model.Registered
import java.util.zip.Inflater

class AuthAdapter( var listOfUsers : MutableList<LoginUser>) : RecyclerView.Adapter<AuthAdapter.AdaptViewHolder>() {



    class AdaptViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        var firstName = itemView.findViewById<TextView>(R.id.name)
        var email = itemView.findViewById<TextView>(R.id.email)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdaptViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recylerviewlayout, parent, false)

        return AdaptViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdaptViewHolder, position: Int) {

        holder.firstName.text = listOfUsers[position].name
        holder.email.text = listOfUsers[position].email

    }

    override fun getItemCount(): Int {
        return listOfUsers.size
    }

}