package com.issam.example

import android.R
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.issam.example.com.issam.example.glide.GlideApp
import de.hdodenhof.circleimageview.CircleImageView
import java.io.ByteArrayOutputStream
import java.util.*


class ProfileActivity : AppCompatActivity() {

    private lateinit var userName:String
    private lateinit var userEmail:String
    private lateinit var userTel:String
    private lateinit var userPassword:String
    private lateinit var userPower:String
    private lateinit var userBild:String


    lateinit var user: FirebaseUser
    lateinit var reference: DatabaseReference
    lateinit var userID:String
    lateinit var bild_change: CircleImageView
    private val storageInstance: FirebaseStorage by lazy {
        FirebaseStorage.getInstance()
    }
    private val currentUserStorage: StorageReference
    get() = storageInstance.reference.child(FirebaseAuth.getInstance().currentUser.uid.toString())

    private val firestoreInstance : FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }
    private val currentUserDocRef : DocumentReference
    get() = firestoreInstance.document("users/${FirebaseAuth.getInstance().currentUser.uid.toString()}")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.issam.example.R.layout.activity_profile)
        user = FirebaseAuth.getInstance().currentUser
        reference = FirebaseDatabase.getInstance().getReference("Users")
        userID = user.uid
        Toast.makeText(baseContext, userID, Toast.LENGTH_LONG).show()
        lateinit var input_name: EditText
        lateinit var input_email: EditText
        lateinit var input_tel: EditText
        lateinit var input_password: EditText
        lateinit var btn_update: Button

        bild_change = findViewById(com.issam.example.R.id.bild_change)
        input_name = findViewById(com.issam.example.R.id.input_name)
        input_email = findViewById(com.issam.example.R.id.input_email)
        input_tel = findViewById(com.issam.example.R.id.input_tel)
        input_password = findViewById(com.issam.example.R.id.input_password)
        btn_update = findViewById(com.issam.example.R.id.btn_update)


        getUserInfo { user ->
            userName = user.fullname.toString()
            userEmail = user.email.toString()
            userTel = user.tel.toString()
            userPassword = user.password.toString()
            userPower = user.userPower.toString()
            userBild = user.userPower.toString()
            if(user.profilBild.toString().isNotEmpty()){
                GlideApp.with(this@ProfileActivity)
                    .load(storageInstance.getReference(user.profilBild.toString()))
                    .placeholder(R.drawable.picture_frame)
                    .into(bild_change)
            }

        }

        val ordersRef = reference.child(userID)
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val email = dataSnapshot.child("email").getValue(String::class.java)
                val fullname = dataSnapshot.child("fullname").getValue(String::class.java)
                val tel = dataSnapshot.child("tel").getValue(String::class.java)
                val password = dataSnapshot.child("password").getValue(String::class.java)

                input_name.setText(fullname)
                input_email.setText(email)
                input_tel.setText(tel)
                input_password.setText(password)

            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        ordersRef.addValueEventListener(valueEventListener)

        // Update Infos

        btn_update.setOnClickListener {
            val input_email_new: Editable? = input_email.text
            val input_name_new:Editable? = input_name.text
            val input_password_new: Editable? = input_password.text
            val input_tel_new: Editable?= input_tel.text
            var afterUpdate = User(input_email_new.toString(),input_name_new.toString(),input_password_new.toString(),input_tel_new.toString(),"","")
            ordersRef?.setValue(afterUpdate)
            Toast.makeText(baseContext,"Alles ist gut gelaufen !", Toast.LENGTH_LONG).show()
        }

        // Update Bild

        bild_change.setOnClickListener {
                val intentImage = Intent().apply {
                    type="image/*"
                    action = Intent.ACTION_GET_CONTENT
                    putExtra(Intent.EXTRA_MIME_TYPES,arrayOf("image/jpeg", "image/png"))
                }

                startActivityForResult(Intent.createChooser(intentImage,"Suchen Sie Ihres Bild aus !"),2) // Problem :man darf hier nicht direkt startActivity aufrufen , weil wir ein Result brauchen ! muss ForResult - der INT ist egal
        }

    }

    override fun onActivityResult(requestCode: Int , resultCode: Int , data: Intent?) {
        super.onActivityResult(requestCode , resultCode , data)
        if(requestCode == 2 && resultCode == Activity.RESULT_OK &&  data != null && data.data != null){
            bild_change.setImageURI(data.data)
            // Jetzt besser wenn wir die Bildgröße kleiner machen ..

            val selectedImagePath = data.data
            val selectedImageBmp = MediaStore.Images.Media.getBitmap(this.contentResolver,selectedImagePath)
            val outputStream = ByteArrayOutputStream()
            selectedImageBmp.compress(Bitmap.CompressFormat.JPEG,20,outputStream)
            val selectedImageBytes =  outputStream.toByteArray()
            uploadProfileBild(selectedImageBytes) {path ->
                val userFiledMap = mutableMapOf<String,Any>()
                userFiledMap["email"] = userEmail
                userFiledMap["fullname"] = userName
                userFiledMap["password"] = userPassword
                userFiledMap["tel"] = userTel
                userFiledMap["userPower"] = userPower
                userFiledMap["profilBild"] = path
                currentUserDocRef.update(userFiledMap)

            }
        }
    }

    private fun uploadProfileBild(selectedImageBytes: ByteArray , onSuccess:(imagePath : String) -> Unit) {
            val ref = currentUserStorage.child("ProfileBild/${UUID.nameUUIDFromBytes(selectedImageBytes)}")
            ref.putBytes(selectedImageBytes).addOnCompleteListener{
                if (it.isSuccessful){
                    onSuccess(ref.path)

                }else{
                    Toast.makeText(this,"Error : ${UUID.nameUUIDFromBytes(selectedImageBytes)} ",Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun getUserInfo(onComplete:(User) -> Unit){
        currentUserDocRef.get().addOnSuccessListener {
            onComplete(it.toObject(User::class.java)!!)
        }
    }



}