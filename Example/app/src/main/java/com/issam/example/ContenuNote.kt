package com.issam.example

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.issam.example.importActivity.NotizenActivity
import kotlinx.android.synthetic.main.activity_contenu_note.*

class ContenuNote : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contenu_note)

        var title = intent.extras?.getString("Title_Key")
        var note = intent.extras?.getString("Note_Key")
        var time = intent.extras?.getString("Time_Key")

        title_text_view.text = title
        note_text_view.text = note
        date.text = time

        rjaa.setOnClickListener {
            startActivity(Intent(applicationContext , NotizenActivity::class.java))
        }
    }
}