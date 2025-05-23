package com.example.krishna

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {
    private lateinit var name: TextView
    private lateinit var userRole: TextView
    private lateinit var userEmail: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Back Button
        findViewById<ImageView>(R.id.backArrow).setOnClickListener {
            finish()
        }

        // Dark Mode Switch
        val darkModeSwitch: Switch = findViewById(R.id.darkModeSwitch)
        darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Enable dark mode
            } else {
                // Disable dark mode
            }
        }

        // Notifications
        findViewById<TextView>(R.id.notifications).setOnClickListener {
            startActivity(Intent(this, NotificationsActivity::class.java))
        }

        // Change Password
        findViewById<TextView>(R.id.changePassword).setOnClickListener {
            startActivity(Intent(this, ChangePasswordActivity::class.java))
        }
        name = findViewById(R.id.name)
        val Vname = getSharedPreferences("APPSF", MODE_PRIVATE).getString("NAME","")
        name.text=Vname;
        userRole = findViewById(R.id.userRole)
        val VuserRole = getSharedPreferences("APPSF", MODE_PRIVATE).getString("USER_TYPE","")
        userRole.text=VuserRole;
        userEmail = findViewById(R.id.userEmail)
        val VuserEmail = getSharedPreferences("APPSF", MODE_PRIVATE).getString("USER_EMAIL","")
        userEmail.text=VuserEmail;
    }
}
