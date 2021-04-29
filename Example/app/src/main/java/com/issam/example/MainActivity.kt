package com.issam.example

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.issam.askworms_demo1.LoginActivity
import com.issam.askworms_demo1.adapter.OnBoardingViewPagerAdapter
import com.issam.askworms_demo1.model.OnBoardingData

open class MainActivity : AppCompatActivity() {

    var onBoardingViewPagerAdapter : OnBoardingViewPagerAdapter? = null
    var tabLayout: TabLayout? = null
    var onBoardingViewPager : ViewPager? = null
    var next : TextView? = null
    var position = 0
    var sharedPreferences : SharedPreferences? = null

    override fun onCreate(savedInstanceState : Bundle?) {

        super.onCreate(savedInstanceState)
        // check if on boarding has already been completed & skip it
        if (restorePrefData()) {
            // go to LoginActivity
            startActivity(Intent(applicationContext, LoginActivity::class.java))
            // terminating current Activity
            finish()
        }

        setContentView(R.layout.activity_main)

        tabLayout = findViewById(R.id.tab_indicator);
        next = findViewById(R.id.next);

        // add data to our model class
        val onBoardingData : MutableList<OnBoardingData> = ArrayList()
        onBoardingData.add(OnBoardingData(title = getString(R.string.slide_heading_text1), desc = getString(R.string.slide_desc_text1), R.drawable.icon1))
        onBoardingData.add(OnBoardingData(title = getString(R.string.slide_heading_text2), desc = getString(R.string.slide_desc_text2), R.drawable.icon2))
        onBoardingData.add(OnBoardingData(title = getString(R.string.slide_heading_text3), desc = getString(R.string.slide_desc_text3), R.drawable.icon3))

        setOnBoardingViewPagerAdapter(onBoardingData)

        val lastTabPosition = onBoardingData.size - 1
        position = onBoardingViewPager!!.currentItem

        next?.setOnClickListener {

            when (position) {
                in 0 until lastTabPosition -> onBoardingViewPager!!.currentItem = (++position)
                // defines what happens after last tab
                else -> {
                    savePrefData()
                    // go to LoginActivity after clicking 'Los geht's!'
                    startActivity(Intent(applicationContext, LoginActivity::class.java))
                }
            }
        }
        // check if tab states has changed
        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            // active tab
            override fun onTabSelected(tab: TabLayout.Tab?) {
                // get current tab position
                position = tab!!.position
                // change text at bottom right on the last page
                when (tab.position) {
                    lastTabPosition -> next!!.text = getString(R.string.los_gehts)
                    else -> next!!.text = getString(R.string.naechste)
                }
            }

            override fun onTabUnselected(tab : TabLayout.Tab?) {
            }

            override fun onTabReselected(tab : TabLayout.Tab?) {
            }
        })
    }

    private fun setOnBoardingViewPagerAdapter(onBoardingData : List<OnBoardingData>) {

        onBoardingViewPager = findViewById(R.id.screenPager)
        onBoardingViewPagerAdapter = OnBoardingViewPagerAdapter(this, onBoardingData)
        onBoardingViewPager!!.adapter = onBoardingViewPagerAdapter
        tabLayout?.setupWithViewPager(onBoardingViewPager)
    }
    // set flag in sharedPreferences
    private fun savePrefData() {

        sharedPreferences = applicationContext.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sharedPreferences!!.edit()
        editor.putBoolean("isOnBoardingCompleted", false) // set to true after DEBUG
        editor.apply()
    }
    // read flag from sharedPreferences
    private fun restorePrefData() : Boolean {

        sharedPreferences = applicationContext.getSharedPreferences("pref", Context.MODE_PRIVATE)
        return sharedPreferences!!.getBoolean("isOnBoardingCompleted",false)
    }
}