package com.issam.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.issam.example.fragments.FeedsFragment
import com.issam.example.fragments.ForumFragment
import com.issam.example.fragments.NotesFragment

class ContentMainActivity : AppCompatActivity() {
/*  private val feedsFragment = FeedsFragment()
 private val forumFragment = ForumFragment()
 private val notesFragment = NotesFragment()
 lateinit var bottom_navigation: BottomNavigationView*/
 override fun onCreate(savedInstanceState: Bundle?) {
     super.onCreate(savedInstanceState)
     setContentView(R.layout.content_main)

    /* bottom_navigation = findViewById(R.id.bottom_navigation)
     bottom_navigation.setOnNavigationItemSelectedListener(this)
     setFragment(feedsFragment)*/
 }
 /*
  override fun onNavigationItemSelected(item: MenuItem): Boolean {
      when (item.itemId) {

          R.id.navigation_home -> {
              setFragment(feedsFragment)
              return true
          }
          R.id.navigation_notizen -> {
              setFragment(notesFragment)
              return true
          }
          R.id.navigation_forum -> {
              setFragment(forumFragment)
              return true
          }
          R.id.moodle -> {
              setFragment(notesFragment)
              return true
          }
          else -> return false
      }
  }


  private fun setFragment(fragment: Fragment) {

      val fr = supportFragmentManager.beginTransaction()
      fr.replace(R.id.coordinator_main_content , fragment)
      fr.commit()

  }*/
}