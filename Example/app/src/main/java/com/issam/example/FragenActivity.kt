package com.issam.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.issam.example.com.issam.example.Frage
import kotlinx.android.synthetic.main.activity_fragen.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.add_frage.*
import kotlinx.android.synthetic.main.add_frage.view.*
import kotlinx.android.synthetic.main.add_note.view.*
import kotlinx.android.synthetic.main.fragment_notes.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class FragenActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    var mRef:DatabaseReference ? = null
    var mFragenList: ArrayList<Frage>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragen)
        auth = Firebase.auth
        val database = FirebaseDatabase.getInstance()
        mRef = database.getReference("fragen")
        mFragenList = ArrayList()

        add_new_frage.setOnClickListener {
            showDialogAddFrage()
        }
    }

    private fun showDialogAddFrage() {

        val alertBuilder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.add_frage,null)
        alertBuilder.setView(view)
        val alertDialog = alertBuilder.create()
        alertDialog.show()
        view.btnSaveFrage.setOnClickListener {
            val title = view.fragentitel_input.text.toString()
            val ffrage = view.fragencotenu_input.text.toString()
            if(title.isNotEmpty() && ffrage.isNotEmpty() ){

                val db = FirebaseFirestore.getInstance()
                val itemfrage: MutableMap<String,Any> = HashMap()
                itemfrage["title"] = title
                itemfrage["contenu"] = ffrage
                itemfrage["studiengand"] = "informatik"
                itemfrage["time"] = getCurrentDate()
                itemfrage["id_user"] = auth.currentUser.uid


                db.collection("fragen")
                    .add(itemfrage)
                    .addOnSuccessListener {
                        alertDialog.dismiss()
                        Toast.makeText(this,"ADDED!", Toast.LENGTH_LONG).show()
                    }
                    .addOnFailureListener{
                        Toast.makeText(this,"PROBLEM!", Toast.LENGTH_LONG).show()
                    }

            }else{
                Toast.makeText(this,"Du musst die 2 obenen Felder ausfüllen!", Toast.LENGTH_LONG).show()
            }

        }


    }


    fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        val mdformat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val strDate = mdformat.format(calendar.time)
        return strDate.toString()
    }
}