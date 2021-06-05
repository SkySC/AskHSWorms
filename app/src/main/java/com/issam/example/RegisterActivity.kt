package com.issam.askworms_demo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.issam.example.R
import com.issam.example.User


class RegisterActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    private val firestoreInstance : FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = Firebase.auth


        lateinit var btnRegister: Button
        btnRegister= findViewById(R.id.btnRegister)
        progressBar= findViewById(R.id.progressBar)
        btnRegister.setOnClickListener {
            signUpUser()
        }

    }

    // Verification Methode !

    private fun signUpUser(){
        lateinit var editTextName: EditText
        lateinit var editTextEmail: EditText
        lateinit var editPassword: EditText
        lateinit var editPhone: EditText

        editTextName = findViewById(R.id.editTextName)
        editTextEmail = findViewById(R.id.editTextEmail)
        editPassword = findViewById(R.id.editPassword)
        editPhone = findViewById(R.id.editPhone)
        if(editTextName.text.toString().isEmpty()){
            editTextName.error ="Geben Sie Ihre Name ein!"
            editTextName.requestFocus()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(editTextEmail.text.toString()).matches()){
            editTextEmail.error ="Geben Sie eine Email Adresse!"
            editTextEmail.requestFocus()
            return
        }
        if(editPassword.text.toString().isEmpty()){
            editPassword.error ="Geben Sie ein Password ein!"
            editPassword.requestFocus()
            return
        }
        if(editPassword.text.toString().length < 6){
            editPassword.error ="Ihre Password ist sehr schwach!"
            editPassword.requestFocus()
            return
        }

        if(editPhone.text.toString().isEmpty()){
            editPhone.error ="Geben Sie eine richtige Tel. Nummer ein!"
            editPhone.requestFocus()
            return
        }

        progressBar.setVisibility(View.VISIBLE)

        auth.createUserWithEmailAndPassword(editTextEmail.text.toString(), editPassword.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    var user = User(
                        editTextName.text.toString(),
                        editTextEmail.text.toString(),
                        editPhone.text.toString(),
                        editPassword.text.toString(),
                        "",
                        ""
                    )
                    // Für Database
                    val currentUserDocRef  : DocumentReference
                    currentUserDocRef = firestoreInstance.collection("users").document(auth.currentUser.uid.toString())
                    currentUserDocRef.set(user)
                    
                    FirebaseDatabase.getInstance().getReference("Users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().uid)
                        .setValue(user).addOnCompleteListener(OnCompleteListener {

                            if (task.isSuccessful) {
                                Toast.makeText(
                                    baseContext,
                                    "User has been registred succefully .",
                                    Toast.LENGTH_LONG
                                ).show()
                                progressBar.setVisibility(View.GONE)

                            } else {
                                Toast.makeText(
                                    baseContext,
                                    "Failed to register  ! Try aggain .",
                                    Toast.LENGTH_LONG
                                ).show()
                                progressBar.setVisibility(View.GONE)
                            }

                        })



                    startActivity(Intent(applicationContext, LoginActivity::class.java))
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                   Toast.makeText(baseContext,"Signup failed .",Toast.LENGTH_LONG).show()
                    progressBar.setVisibility(View.GONE)

                }
            }





    }
}