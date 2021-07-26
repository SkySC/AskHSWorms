package com.issam.example.reset_password

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.issam.askworms_demo1.LoginActivity
import com.issam.example.R

class PasswordForgotActivity : AppCompatActivity() {

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
            startActivity(Intent(applicationContext , LoginActivity::class.java))
            finish()
        }

        resetPasswordBtn.setOnClickListener {
            resetPassword()
        }
    }

    private fun resetPassword() {

        var email = emailEditText.text.toString().trim()

        if (email.isEmpty()) {
            emailEditText.setError("Das Feld E-Mail ist leer!")
            emailEditText.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.error = "Ungültiges E-Mail Format!"
            emailEditText.requestFocus()
            return
        }

        progressBar.setVisibility(View.VISIBLE)

        auth.sendPasswordResetEmail(email).addOnCompleteListener { listener ->
            if (listener.isSuccessful) {
                Toast.makeText(
                    baseContext , "E-Mail erfolgreich verschickt" ,
                    Toast.LENGTH_LONG
                ).show()
                progressBar.setVisibility(View.GONE)
                startActivity(Intent(applicationContext , PasswordDoneActivity::class.java))
                finish()
            } else {
                Toast.makeText(
                    baseContext , "Es tut uns Leid, etwas ist schiefgelaufen! Probiere es erneut" ,
                    Toast.LENGTH_LONG
                ).show()

                progressBar.setVisibility(View.GONE)
            }
        }
    }
}