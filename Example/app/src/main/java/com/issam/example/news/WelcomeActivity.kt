package com.issam.example.news

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.storage.FirebaseStorage
import com.issam.askworms_demo1.LoginActivity
import com.issam.example.notes.NoteActivity
import com.issam.example.ProfileActivity
import com.issam.example.R
import com.issam.example.com.issam.example.adapter.NewsAdapter
import com.issam.example.com.issam.example.glide.GlideApp
import com.issam.example.com.issam.example.model.News
import com.issam.example.com.issam.example.model.User
import com.issam.example.forum.ForumActivity
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.OnItemClickListener
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import de.hdodenhof.circleimageview.CircleImageView

class WelcomeActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener {

    lateinit var btnRight: ImageView
    lateinit var btnLeft: ImageView
    lateinit var userImage: CircleImageView
    lateinit var nameUser: TextView
    private lateinit var chatSection: Section
    lateinit var mRecyclerView: RecyclerView

    private val firestoreInstance: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    // um das Bild in LoadFunction zu bekommen - hier haben wir wir get Root von Storage
    private val storageInstance: FirebaseStorage by lazy {
        FirebaseStorage.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        lateinit var toolbar1: Toolbar
        lateinit var drawerLayout: DrawerLayout
        lateinit var navigationView: NavigationView

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        userImage = findViewById(R.id.userImage)
        nameUser = findViewById(R.id.nameUser)
        mRecyclerView = findViewById(R.id.mRecyclerView)

        addChatListener(::initRecyclerView)

        // Database get document von Firebase -> Ster by id
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
                    userImage.setImageResource(R.drawable.student)
                }
            }

        btnRight = findViewById(R.id.right)
        btnLeft = findViewById(R.id.left)

        btnRight.setOnClickListener {
            startActivity(Intent(applicationContext , NoteActivity::class.java))
        }

        btnLeft.setOnClickListener {
            Toast.makeText(
                baseContext ,
                "Du bist bereits auf der ersten Seite!" ,
                Toast.LENGTH_LONG
            ).show()
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


    private fun addChatListener(onListen: (List<Item>) -> Unit): ListenerRegistration {
        return firestoreInstance.collection("news")
            .addSnapshotListener { querySnapshot , firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    return@addSnapshotListener
                }

                val items = mutableListOf<Item>()
                querySnapshot!!.documents.forEach {
                    items.add(NewsAdapter(it.toObject(News::class.java)!! , this))

                }
                onListen(items)
            }
    }

    @SuppressLint("WrongConstant")
    private fun initRecyclerView(item: List<Item>) {
        mRecyclerView.layoutManager = LinearLayoutManager(this , LinearLayout.VERTICAL , false)
        mRecyclerView.apply {
            adapter = GroupAdapter<GroupieViewHolder>().apply {
                chatSection = Section(item)
                add(chatSection)
                setOnItemClickListener(onItemClick)
            }
        }
    }

    val onItemClick = OnItemClickListener { item , view ->
        if (item is NewsAdapter) {
            item.news.titleNews
            item.news.ContenuNews
            item.news.mNewsPhoto
            item.news.dateNews

            val intent = Intent(applicationContext , NewsContentActivity::class.java)

            intent.putExtra("title" , item.news.titleNews)
            intent.putExtra("contenu" , item.news.ContenuNews)
            intent.putExtra("img" , item.news.mNewsPhoto)
            intent.putExtra("date" , item.news.dateNews)
            startActivity(intent)
        }
    }
}