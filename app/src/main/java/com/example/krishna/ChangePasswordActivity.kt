package com.example.krishna

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ChangePasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        val currentPassword = findViewById<EditText>(R.id.etCurrentPassword)
        val newPassword = findViewById<EditText>(R.id.etNewPassword)
        val confirmPassword = findViewById<EditText>(R.id.etConfirmPassword)
        val btnChangePassword = findViewById<Button>(R.id.btnChangePassword)

        btnChangePassword.setOnClickListener {
            val currentPass = currentPassword.text.toString()
            val newPass = newPassword.text.toString()
            val confirmPass = confirmPassword.text.toString()

            if (currentPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else if (newPass != confirmPass) {
                Toast.makeText(this, "New password and Confirm password must match", Toast.LENGTH_SHORT).show()
            } else {
                // Implement actual password change logic here (e.g., update in database)
                Toast.makeText(this, "Password changed successfully", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
