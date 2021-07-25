package com.issam.example

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.issam.askworms_demo1.LoginActivity

class PasswordDone : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_done)

        var zurickBtn: ImageView
        zurickBtn = findViewById(R.id.zurick)

        zurickBtn.setOnClickListener {
            startActivity(Intent(applicationContext , LoginActivity::class.java))
            finish()
        }
    }
}