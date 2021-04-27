package com.issam.example

import android.R
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*


class ProfileActivity : WelcomeActivity() {
    lateinit var user: FirebaseUser
    lateinit var reference: DatabaseReference
    lateinit var userID:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.issam.example.R.layout.activity_profile)
        user = FirebaseAuth.getInstance().currentUser
        reference = FirebaseDatabase.getInstance().getReference("Users")
        userID = user.uid

        lateinit var input_name: EditText
        lateinit var input_email: EditText
        lateinit var input_tel: EditText
        lateinit var input_password: EditText
        input_name = findViewById(com.issam.example.R.id.input_name)
        input_email = findViewById(com.issam.example.R.id.input_email)
        input_tel = findViewById(com.issam.example.R.id.input_tel)
        input_password = findViewById(com.issam.example.R.id.input_password)

        /*
        reference.child(userID).addListenerForSingleValueEvent(object : ValueEventListener(){
            override fun onDataChange(snapshot: DataSnapshot) {
                var user = User()
                val post = snapshot.getValue<user>()
            }

            override fun onCancelled(error: DatabaseError) {
                println(error!!.message)
            }

        })
*/



    }
}