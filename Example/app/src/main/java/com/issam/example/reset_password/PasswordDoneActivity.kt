package com.issam.example.reset_password

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.issam.askworms_demo1.LoginActivity
import com.issam.example.R

class PasswordDoneActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_done)

        val zurueckBtn: ImageView = findViewById(R.id.zurick)

        zurueckBtn.setOnClickListener {
            startActivity(Intent(applicationContext , LoginActivity::class.java))
            finish()
        }
    }
}