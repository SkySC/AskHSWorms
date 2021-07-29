package com.issam.example

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.issam.askworms_demo1.LoginActivity
import com.issam.askworms_demo1.adapter.OnboardingViewPagerAdapter
import com.issam.example.model.Onboarding

open class MainActivity : AppCompatActivity() {

    var onboardingViewPagerAdapterRef: OnboardingViewPagerAdapter? = null
    var tabLayoutRef: TabLayout? = null
    var onboardingViewPager: ViewPager? = null
    var nextView: TextView? = null
    var currentTab = 0
    var sharedPreferencesRef: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        // check if on boarding has already been completed & skip it
        if (readFromSharedPreferences()) {
            // go to LoginActivity
            startActivity(Intent(applicationContext , LoginActivity::class.java))
            // terminating current Activity
            finish()
        }

        setContentView(R.layout.activity_main)

        tabLayoutRef = findViewById(R.id.tab_indicator);
        nextView = findViewById(R.id.next);

        // add data to our model class
        val onboardingList: MutableList<Onboarding> = ArrayList()
        onboardingList.add(
            Onboarding(
                tabTitle = getString(R.string.slide_heading_text1) ,
                tabDescription = getString(R.string.slide_desc_text1) ,
                R.drawable.icon1
            )
        )

        onboardingList.add(
            Onboarding(
                tabTitle = getString(R.string.slide_heading_text2) ,
                tabDescription = getString(R.string.slide_desc_text2) ,
                R.drawable.icon2
            )
        )

        onboardingList.add(
            Onboarding(
                tabTitle = getString(R.string.slide_heading_text3) ,
                tabDescription = getString(R.string.slide_desc_text3) ,
                R.drawable.icon3
            )
        )

        setOnboardingViewPagerAdapter(onboardingList)

        val lastTab = onboardingList.size - 1
        currentTab = onboardingViewPager!!.currentItem

        nextView?.setOnClickListener {

            when (currentTab) {
                in 0 until lastTab -> onboardingViewPager!!.currentItem = (++currentTab)
                // defines what happens after last tab
                else -> {
                    saveInSharedPreferences()
                    // go to LoginActivity after clicking 'Los geht's!'
                    startActivity(Intent(applicationContext , LoginActivity::class.java))
                }
            }
        }
        // check if tab states has changed
        tabLayoutRef!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            // active tab
            override fun onTabSelected(tab: TabLayout.Tab?) {
                // get current tab position
                currentTab = tab!!.position
                // change text at bottom right on the last page
                when (tab.position) {
                    lastTab -> nextView!!.text = getString(R.string.los_gehts)
                    else -> nextView!!.text = getString(R.string.naechste)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun setOnboardingViewPagerAdapter(onboardingList: List<Onboarding>) {

        onboardingViewPager = findViewById(R.id.screenPager)
        onboardingViewPagerAdapterRef = OnboardingViewPagerAdapter(this , onboardingList)
        onboardingViewPager!!.adapter = onboardingViewPagerAdapterRef
        tabLayoutRef?.setupWithViewPager(onboardingViewPager)
    }

    // set flag in sharedPreferences
    private fun saveInSharedPreferences() {

        sharedPreferencesRef = applicationContext.getSharedPreferences("pref" , Context.MODE_PRIVATE)
        val sharedPreferencesEditor: SharedPreferences.Editor = sharedPreferencesRef!!.edit()
        sharedPreferencesEditor.putBoolean("isOnboardingCompleted" , true)
        sharedPreferencesEditor.apply()
    }

    // read flag from sharedPreferences
    private fun readFromSharedPreferences(): Boolean {

        sharedPreferencesRef = applicationContext.getSharedPreferences("pref" , Context.MODE_PRIVATE)
        return sharedPreferencesRef!!.getBoolean("isOnboardingCompleted" , false)
    }
}