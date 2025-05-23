package com.example.krishna

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class IntroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_into)

        val getStartedButton: Button = findViewById(R.id.btn_get_started)

        getStartedButton.setOnClickListener {
            val intent = Intent(this,TeacherRegisterActivity::class.java) // Replace with your next activity
            startActivity(intent)
            finish()
        }
    }
}
