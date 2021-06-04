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
import com.issam.example.ForgotPassword
import com.issam.example.R
import com.issam.example.WelcomeActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        lateinit var btnlogin:Button
        lateinit var password_reset:TextView
        lateinit var btnsignUp:ImageView
        progressBar= findViewById(R.id.progressBar)
        auth = FirebaseAuth.getInstance()
        btnlogin= findViewById(R.id.btn_login)
        btnsignUp= findViewById(R.id.btnRegistration)
        password_reset= findViewById(R.id.password_reset)


        auth = Firebase.auth

        ///
        btnsignUp.setOnClickListener{
            startActivity(Intent(applicationContext, RegisterActivity::class.java))
            finish()
        }
        password_reset.setOnClickListener{
            startActivity(Intent(applicationContext, ForgotPassword::class.java))
            finish()
        }


        // Jetzt onClick
        btnlogin.setOnClickListener {
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
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
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
        val currentUser = auth.currentUser
        if(currentUser != null){
            updateUI(currentUser)
        }
    }

    fun updateUI(currentUser : FirebaseUser?){
    }

}