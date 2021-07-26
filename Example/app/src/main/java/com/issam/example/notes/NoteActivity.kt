package com.issam.example.notes

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.issam.askworms_demo1.LoginActivity
import com.issam.example.*
import com.issam.example.com.issam.example.glide.GlideApp
import com.issam.example.com.issam.example.model.Note
import com.issam.example.adapter.NoteAdapter
import com.issam.example.com.issam.example.model.User
import com.issam.example.forum.ForumActivity
import com.issam.example.news.WelcomeActivity
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.add_note.view.*
import kotlinx.android.synthetic.main.delete_note.view.*
import kotlinx.android.synthetic.main.fragment_notes.*
import java.text.SimpleDateFormat
import java.util.*
import com.issam.example.R
import kotlin.collections.ArrayList

class NoteActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener {

    lateinit var btnRight: ImageView
    lateinit var btnLeft: ImageView
    lateinit var toolbar1: Toolbar
    lateinit var userImage: CircleImageView
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var nameUser: TextView
    lateinit var add: ImageView
    var mNoteList: ArrayList<Note>? = null

    var mRef: DatabaseReference? = null

    private val firestoreInstance: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    // um das Bild in LoadFunction zu bekommen - hier haben wir wir get Root von Storage
    private val storageInstance: FirebaseStorage by lazy {
        FirebaseStorage.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notizen)
        userImage = findViewById(R.id.userImage)
        nameUser = findViewById(R.id.nameUser)
        add = findViewById(R.id.add)

        val database = FirebaseDatabase.getInstance()
        mRef = database.getReference("Notes")
        mNoteList = ArrayList()

        add.setOnClickListener {
            showDialogAddNote()
        }

        note_list_view.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>? ,
                view: View? ,
                position: Int ,
                id: Long
            ) {
                var mynote = mNoteList?.get(position)!!
                var title = mynote.title
                var note = mynote.note
                var tarikh = mynote.timestamp
                var noteIntent = Intent(this@NoteActivity , NoteContentActivity::class.java)

                noteIntent.putExtra("Title_Key" , title)
                noteIntent.putExtra("Note_Key" , note)
                noteIntent.putExtra("Time_Key" , tarikh)
                startActivity(noteIntent)
            }
        }

        note_list_view.onItemLongClickListener = object : AdapterView.OnItemLongClickListener {
            override fun onItemLongClick(
                parent: AdapterView<*>? ,
                view: View? ,
                position: Int ,
                id: Long
            ): Boolean {
                val alertBuilder = AlertDialog.Builder(this@NoteActivity)
                val view = layoutInflater.inflate(R.layout.delete_note , null)
                val alertDialog = alertBuilder.create()

                alertDialog.setView(view)
                alertDialog.show()

                var myNote = mNoteList?.get(position)!!
                var title = myNote.title
                var note = myNote.note
                view.delete_title.setText(title)
                view.delete_note.setText(note)

                view.btnUpdateNote.setOnClickListener {
                    var childRef = mRef?.child(myNote.id.toString())
                    var title = view.delete_title.text.toString()
                    var note = view.delete_note.text.toString()

                    var afterUpdate = Note(myNote.id , title , note , getCurrentDate())
                    childRef?.setValue(afterUpdate)
                    alertDialog.dismiss()
                }

                view.btnDeletNote.setOnClickListener {
                    mRef?.child(myNote.id.toString())?.removeValue()
                    Toast.makeText(baseContext , "Die Notizen ist gelöscht" , Toast.LENGTH_SHORT)
                        .show()
                    alertDialog.dismiss()
                }

                return false
            }
        }

        firestoreInstance.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid.toString())
            .get()
            .addOnSuccessListener {
                val user = it.toObject(User::class.java) // casting to Object
                nameUser.text = user!!.fullname

                if (user!!.profilBild?.isNotEmpty()) {
                    GlideApp.with(this)
                        .load(storageInstance.getReference(user.profilBild))
                        .into(userImage)
                } else {
                    userImage.setImageResource(R.drawable.dozent)
                }
            }

        btnRight = findViewById(R.id.right)
        btnLeft = findViewById(R.id.left)

        btnRight.setOnClickListener {
            startActivity(Intent(applicationContext , ForumActivity::class.java))
        }

        btnLeft.setOnClickListener {
            startActivity(Intent(applicationContext , WelcomeActivity::class.java))
        }

        drawerLayout = findViewById(R.id.drawerLayout)
        toolbar1 = findViewById(R.id.toolbar1)
        navigationView = findViewById(R.id.nav_view)
        setSupportActionBar(toolbar1)

        val toggle = ActionBarDrawerToggle(
            this , drawerLayout , toolbar1 , 0 , 0
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navigationView.setNavigationItemSelectedListener(this)
    }

    fun showDialogAddNote() {
        val alertBuilder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.add_note , null)
        alertBuilder.setView(view)
        val alertDialog = alertBuilder.create()
        alertDialog.show()

        view.save_note.setOnClickListener {
            val title = view.title_edittext.text.toString()
            val note = view.note_edittext.text.toString()

            if (title.isNotEmpty() && note.isNotEmpty()) {
                var id = mRef!!.push().key
                var myNote = Note(id , title , note , getCurrentDate())

                if (id != null) {
                    mRef!!.child(id).setValue(myNote)
                }

                alertDialog.dismiss()
            } else {
                Toast.makeText(this , "Empty" , Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                startActivity(Intent(applicationContext , WelcomeActivity::class.java))
            }

            R.id.forum -> {
                startActivity(Intent(applicationContext , ForumActivity::class.java))

            }

            R.id.todos -> {
                startActivity(Intent(applicationContext , NoteActivity::class.java))
            }

            R.id.moodle -> {
                val url = "https://moodle.hs-worms.de/moodle/"
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)
            }

            R.id.lsf -> {
                val url = "https://lsf.hs-worms.de/"
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)
            }

            R.id.webmailer -> {
                val url = "https://webmailer2.hs-worms.de/"
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)
            }

            R.id.profile -> {
                startActivity(Intent(applicationContext , ProfileActivity::class.java))
            }

            R.id.abmelden -> {
                FirebaseAuth.getInstance().signOut()
                Toast.makeText(baseContext , "Abmeldung läuft" , Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext , LoginActivity::class.java))
            }
        }

        return true
    }

    override fun onStart() {
        super.onStart()
        mRef?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                mNoteList?.clear()

                for (n in p0!!.children) {
                    var note = n.getValue(Note::class.java)
                    mNoteList?.add(0 , note!!)
                }

                val noteAdapter = NoteAdapter(applicationContext , mNoteList!!)
                note_list_view.adapter = noteAdapter
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        val mdformat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'" , Locale.GERMANY)
        val strDate = mdformat.format(calendar.time)
        return strDate.toString()
    }
}