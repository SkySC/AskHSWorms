package com.issam.example.notes

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.issam.example.R
import kotlinx.android.synthetic.main.activity_content_note.*

class NoteContentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_note)

        var title = intent.extras?.getString("Title_Key")
        var note = intent.extras?.getString("Note_Key")
        var time = intent.extras?.getString("Time_Key")

        title_text_view.text = title
        note_text_view.text = note
        date.text = time

        rjaa.setOnClickListener {
            startActivity(Intent(applicationContext , NoteActivity::class.java))
        }
    }
}