package com.issam.example.importActivity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.issam.askworms_demo1.LoginActivity
import com.issam.example.*
import com.issam.example.com.issam.example.glide.GlideApp
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_forum.*
import kotlinx.android.synthetic.main.recycler_view_item.view.*
import kotlinx.android.synthetic.main.recycler_view_item_1.view.*

class ForumActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener{

    lateinit var btnRight: ImageView
    lateinit var btnLeft: ImageView
    lateinit var toolbar1: Toolbar
    lateinit var userImage: CircleImageView
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var nameUser: TextView
    lateinit var mDatabase : DatabaseReference


    private val firestoreInstance : FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    // um das Bild in LoadFunction zu bekommen - hier haben wir wir get Root von Storage
    private val storageInstance : FirebaseStorage by lazy {
        FirebaseStorage.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forum)
        userImage= findViewById(R.id.userImage)
        nameUser= findViewById(R.id.nameUser)



      //  logRecyclerView()





        // Database get document von Firebase -> Ster by id
        firestoreInstance.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid.toString())
            .get()
            .addOnSuccessListener {
                val user = it.toObject(User::class.java) // casting to Object
                nameUser.text = user!!.fullname
                if(user!!.profilBild?.isNotEmpty()){
                    GlideApp.with(this)
                        .load(storageInstance.getReference(user.profilBild))
                        .into(userImage)
                }else{
                    userImage.setImageResource(R.drawable.dozent)
                }
            }



        btnRight= findViewById(R.id.right)
        btnLeft= findViewById(R.id.left)
        btnRight.setOnClickListener {
            Toast.makeText(baseContext , "Sie Sind schon da !." , Toast.LENGTH_LONG).show()
        }
        btnLeft.setOnClickListener {
            startActivity(Intent(applicationContext , NotizenActivity::class.java))
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

  /*  private fun logRecyclerView() {

        var FirebaseRecyclerAdapter = object : FirebaseRecyclerAdapter<News,UsersViewHolder>(

            News::class.java,
            R.layout.recycler_view_item_1,
            UsersViewHolder::class.java,
            mDatabase
        ) {

            override fun populateViewHolder(viewHolder: UsersViewHolder? , model: News? , position: Int) {

                viewHolder?.itemView?.textNews?.setText(model?.titleNews)
                viewHolder?.itemView?.contenuas?.setText(model?.contenuNews)
            }

        }
        mRecyclerView.adapter = FirebaseRecyclerAdapter

    }*/


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