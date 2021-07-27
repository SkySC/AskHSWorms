package com.issam.askworms_demo1

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.issam.example.R
import com.issam.example.news.WelcomeActivity
import com.issam.example.reset_password.PasswordForgotActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var btnlogin: Button = findViewById(R.id.btn_login)
        var btnsignUp: ImageView = findViewById(R.id.btnRegistration)
        var password_reset: TextView = findViewById(R.id.password_reset)

        progressBar = findViewById(R.id.progressBar)
        auth = FirebaseAuth.getInstance()

        auth = Firebase.auth

        btnsignUp.setOnClickListener {
            startActivity(Intent(applicationContext , RegisterActivity::class.java))
            finish()
        }
        password_reset.setOnClickListener {
            startActivity(Intent(applicationContext , PasswordForgotActivity::class.java))
            finish()
        }

        btnlogin.setOnClickListener {
            userLogin()

        }
    }

    private fun userLogin() {

        var editTextEmail: EditText = findViewById(R.id.editTextEmail)
        var editTextPassword: EditText = findViewById(R.id.editTextPassword)
        var email = editTextEmail.text.toString()
        var password = editTextPassword.text.toString()

        if (email.isEmpty()) {
            editTextEmail.error = "Das Feld E-Mail ist leer!"
            editTextEmail.requestFocus()

            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.error = "Ungültiges E-Mail Format!"
            editTextEmail.requestFocus()

            return
        }

        if (password.isEmpty()) {
            editTextPassword.error = "Das Feld Passwort ist leer!"
            editTextPassword.requestFocus()

            return
        }

        progressBar.setVisibility(View.VISIBLE)

        auth.signInWithEmailAndPassword(email , password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user.isEmailVerified) {
                        val i = Intent(applicationContext , WelcomeActivity::class.java)
                        startActivity(i)

                    } else {
                        user.sendEmailVerification()
                        Toast.makeText(
                            baseContext ,
                            "Wir haben dir eine E-Mail zur Verifikation gesendet" ,
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    // Sign in success, update UI with the signed-in user's information
                    updateUI(user)
                    progressBar.setVisibility(View.GONE)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext ,
                        "Authentifizierung fehlgeschlagen! Bitte versuche es erneut" ,
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    updateUI(null)
                    progressBar.setVisibility(View.GONE)
                }
            }
    }

    public override fun onStart() {

        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            updateUI(currentUser)
        }
    }

    fun updateUI(currentUser: FirebaseUser?) {}
}