package com.issam.example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_news_content.*

class NewsContentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_content)

        val title = intent.getStringExtra("title")
        val contenu = intent.getStringExtra("contenu")
        val dateNews = intent.getStringExtra("date")

        titleNews.text = title
        contenuas.text = contenu
        date.text = dateNews

        rjaa.setOnClickListener {
            finish()
        }
    }
}