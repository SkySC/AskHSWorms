package com.issam.example

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.issam.askworms_demo1.LoginActivity

open class WelcomeActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {

        lateinit var toolbar1: Toolbar
        lateinit var drawerLayout: DrawerLayout
        lateinit var navigationView: NavigationView

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        //

        drawerLayout = findViewById(R.id.drawerLayout)
        toolbar1 =  findViewById(R.id.toolbar1)
        navigationView = findViewById(R.id.nav_view)
        setSupportActionBar(toolbar1)

        val toggle = ActionBarDrawerToggle(
            this,drawerLayout,toolbar1,0,0
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navigationView.setNavigationItemSelectedListener ( this )

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.home ->{
                Toast.makeText(baseContext,"Rak f home!",Toast.LENGTH_SHORT).show()
            }
            R.id.moodle ->{
                Toast.makeText(baseContext,"Rak f Moodle!",Toast.LENGTH_SHORT).show()
            }
            R.id.profile ->{
                startActivity(Intent(applicationContext, ProfileActivity::class.java))
            }
            R.id.abmelden ->{
                FirebaseAuth.getInstance().signOut()
                Toast.makeText(baseContext,"Abmeldung läuft",Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext, LoginActivity::class.java))
            }
        }
        return true
    }
}