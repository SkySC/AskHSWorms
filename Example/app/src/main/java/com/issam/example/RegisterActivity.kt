package com.issam.askworms_demo1

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.issam.example.R
import com.issam.example.com.issam.example.model.User
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    lateinit var progressBar: ProgressBar
    private lateinit var auth: FirebaseAuth
    private val firestoreInstance: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = Firebase.auth

        val btnRegister: Button = findViewById(R.id.btnRegister)
        progressBar = findViewById(R.id.progressBar)

        btnRegister.setOnClickListener {
            signUpUser()
        }

        rjaa.setOnClickListener {
            startActivity(Intent(applicationContext , LoginActivity::class.java))
            finish()
        }
    }

    // Verification Methode !
    private fun signUpUser() {

        val editTextName: EditText = findViewById(R.id.editTextName)
        val editTextEmail: EditText = findViewById(R.id.editTextEmail)
        val editPassword: EditText = findViewById(R.id.editPassword)
        val editPhone: EditText = findViewById(R.id.editPhone)

        if (editTextName.text.toString().isEmpty()) {
            editTextName.error = "Das Feld Name ist leer!"
            editTextName.requestFocus()

            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(editTextEmail.text.toString()).matches()) {
            editTextEmail.error = "Ungültiges E-Mail Format!"
            editTextEmail.requestFocus()

            return
        }

        if (editPhone.text.toString().isEmpty()) {
            editPhone.error = "Das Feld Telefon ist leer!"
            editPhone.requestFocus()

            return
        }

        if (editPassword.text.toString().isEmpty()) {
            editPassword.error = "Das Feld Passwort ist leer!"
            editPassword.requestFocus()

            return
        }

        if (editPassword.text.toString().length < 6) {
            editPassword.error = "Dein Passwort braucht mindestens 6 Zeichen!"
            editPassword.requestFocus()

            return
        }

        progressBar.setVisibility(View.VISIBLE)

        auth.createUserWithEmailAndPassword(
            editTextEmail.text.toString() ,
            editPassword.text.toString()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = User(
                        editTextName.text.toString() ,
                        editTextEmail.text.toString() ,
                        editPhone.text.toString() ,
                        editPassword.text.toString() ,
                        "" ,
                        ""
                    )
                    // Für Database
                    val currentUserDocRef: DocumentReference = firestoreInstance.collection("users")
                        .document(auth.currentUser.uid)
                    currentUserDocRef.set(user)

                    FirebaseDatabase.getInstance().getReference("Users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().uid)
                        .setValue(user).addOnCompleteListener {
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    baseContext ,
                                    "Deine Registrierung war erfolgreich" ,
                                    Toast.LENGTH_LONG
                                ).show()
                                progressBar.setVisibility(View.GONE)
                            } else {
                                Toast.makeText(
                                    baseContext ,
                                    "Die Registrierung ist fehlgeschlagen! Versuche es erneut" ,
                                    Toast.LENGTH_LONG
                                ).show()
                                progressBar.setVisibility(View.GONE)
                            }
                        }

                    startActivity(Intent(applicationContext , LoginActivity::class.java))
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext ,
                        "Die Registrierung ist fehlgeschlagen! Versuche es erneut" ,
                        Toast.LENGTH_LONG
                    ).show()
                    progressBar.setVisibility(View.GONE)
                }
            }
    }
}