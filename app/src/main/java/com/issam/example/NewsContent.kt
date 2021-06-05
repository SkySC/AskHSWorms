package com.issam.example

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_forum.*
import kotlinx.android.synthetic.main.activity_news_content.*

class NewsContent : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_content)


        val title = intent.getStringExtra("title")
        val contenu = intent.getStringExtra("contenu")
        val imgNews = intent.getStringExtra("img")
        val dateNews = intent.getStringExtra("date")

        titleNews.text = title
        contenuas.text = contenu
        date.text = dateNews
        rjaa.setOnClickListener {
           finish()
        }
    }
}