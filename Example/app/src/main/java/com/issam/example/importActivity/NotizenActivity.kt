package com.issam.example.importActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Insets.add
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.storage.FirebaseStorage
import com.issam.askworms_demo1.LoginActivity
import com.issam.example.*
import com.issam.example.R
import com.issam.example.com.issam.example.adapter.ChatItems
import com.issam.example.com.issam.example.adapter.CustomAdapter
import com.issam.example.com.issam.example.adapter.CustomAdapter.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import de.hdodenhof.circleimageview.CircleImageView


class NotizenActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener {
    lateinit var btnRight: ImageView
    lateinit var btnLeft: ImageView
    lateinit var toolbar1: Toolbar
    lateinit var userImage: CircleImageView
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView


    private val firestoreInstance : FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    // um das Bild in LoadFunction zu bekommen - hier haben wir wir get Root von Storage
    private val storageInstance : FirebaseStorage by lazy {
        FirebaseStorage.getInstance()
    }
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notizen)
        btnRight= findViewById(R.id.right)
        btnLeft= findViewById(R.id.left)
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



/*
        var myArrayList = ArrayList<News>()
        myArrayList.add(News("Hey es muss jetzt klappen !101","Title tiitle2",R.drawable.student))
        myArrayList.add(News("Hey es muss jetzt klappen !101","Title tiitle3",R.drawable.dozent))



        mRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayout.VERTICAL,false)
        //database = FirebaseDatabase.getInstance().getReference("news")
        val customAdapter =  CustomAdapter(myArrayList)
        mRecyclerView.adapter = customAdapter*/

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
                startActivity(Intent(applicationContext , NotizenActivity::class.java))
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


}