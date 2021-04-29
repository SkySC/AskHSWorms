package com.issam.askworms_demo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.issam.example.R
import com.issam.example.WelcomeActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var authentication : FirebaseAuth
    lateinit var progressBar : ProgressBar
    lateinit var loginButton : Button
    lateinit var signUpButton : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        authentication = FirebaseAuth.getInstance()
        progressBar = findViewById(R.id.progressBar)
        loginButton = findViewById(R.id.btn_login)
        signUpButton = findViewById(R.id.btnRegistration)

        authentication = Firebase.auth

        ///
        signUpButton.setOnClickListener{
            startActivity(Intent(applicationContext, RegisterActivity::class.java))
            finish()
        }


        // Jetzt onClick
        loginButton.setOnClickListener {
            userLogin()


        }

    }

    private fun userLogin() {
        lateinit var editTextEmail: EditText
        lateinit var editTextPassword: EditText
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        var email = editTextEmail.text.toString()
        var password = editTextPassword.text.toString()

        if(email.isEmpty()){
            editTextEmail.error ="Geben Sie Ihre Email ein!"
            editTextEmail.requestFocus()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.error ="Geben Sie eine richtige Email Adresse!"
            editTextEmail.requestFocus()
            return
        }
        if(password.isEmpty()){
            editTextPassword.error ="Geben Sie Ihr Password ein!"
            editTextPassword.requestFocus()
            return
        }
        if(password.length < 6){
            editTextPassword.error ="Min Password ist 6 Buchstaben!"
            editTextPassword.requestFocus()
            return
        }

        progressBar.setVisibility(View.VISIBLE)
        authentication.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = authentication.currentUser
                    if(user.isEmailVerified){
                        val i = Intent(applicationContext, WelcomeActivity::class.java)
                        startActivity(i)

                    }else{
                        user.sendEmailVerification()
                        Toast.makeText(baseContext, "Sie müssen Ihre Email verifizieren !", Toast.LENGTH_LONG).show()
                    }

                    // Sign in success, update UI with the signed-in user's information


                    updateUI(user)
                    progressBar.setVisibility(View.GONE)
                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    updateUI(null)
                    progressBar.setVisibility(View.GONE)
                }
            }

    }


    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = authentication.currentUser
        if(currentUser != null){
            updateUI(currentUser)
        }
    }

    fun updateUI(currentUser : FirebaseUser?){
    }

}