package com.issam.example

import android.content.Intent
import android.os.Bundle

import android.util.Patterns
import android.view.View
import android.widget.*

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.issam.askworms_demo1.LoginActivity
import com.issam.askworms_demo1.RegisterActivity

class ForgotPassword : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    lateinit var resetPasswordBtn: Button
    lateinit var btnZurick: ImageView
    lateinit var emailEditText: EditText
    lateinit var progressBar: ProgressBar



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)


        resetPasswordBtn = findViewById(R.id.resetPassword)
        btnZurick = findViewById(R.id.zurick)
        emailEditText = findViewById(R.id.email)
        progressBar = findViewById(R.id.progressBar)

        auth = FirebaseAuth.getInstance()
        btnZurick.setOnClickListener {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
            finish()
        }
        resetPasswordBtn.setOnClickListener{
                resetPassword()
        }



    }

    private fun resetPassword() {
        lateinit var email: String
        email = emailEditText.text.toString().trim()

        if(email.isEmpty()){
            emailEditText.setError("Geben Sie Ihre Email Adresse ein")
            emailEditText.requestFocus()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.error ="Geben Sie eine richtige Email Adresse!"
            emailEditText.requestFocus()
            return
        }

        progressBar.setVisibility(View.VISIBLE)

        auth.sendPasswordResetEmail(email).addOnCompleteListener { listener ->
            if (listener.isSuccessful)
            {
                Toast.makeText(baseContext,"Sie haben eine Email bekommen !", Toast.LENGTH_LONG).show()
                progressBar.setVisibility(View.GONE)
                startActivity(Intent(applicationContext, PasswordDone::class.java))
                finish()
            }
            else {
                Toast.makeText(baseContext, "Etwas ist schief gelaufen , Versuchen Sie es nochmal .", Toast.LENGTH_LONG).show()
                progressBar.setVisibility(View.GONE)
            }

        }

    }


}