package com.example.krishna

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class TeacherRegisterActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var teacherIdEditText: EditText
    private lateinit var teacherIdContainer: LinearLayout
    private lateinit var roleRadioGroup: RadioGroup
    private lateinit var registerButton: Button
    private lateinit var signInText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.teacher_activity_register)

        // Initialize views
        nameEditText = findViewById(R.id.name)
        emailEditText = findViewById(R.id.email)
        usernameEditText = findViewById(R.id.username)
        passwordEditText = findViewById(R.id.password)
        confirmPasswordEditText = findViewById(R.id.confirm_password)
        teacherIdEditText = findViewById(R.id.teacher_id_input)
        teacherIdContainer = findViewById(R.id.teacher_id_container)
        roleRadioGroup = findViewById(R.id.role_radio_group)
        registerButton = findViewById(R.id.btn_register)
        signInText = findViewById(R.id.sign_in_text)

        // Hide teacher ID input initially
        teacherIdContainer.visibility = LinearLayout.GONE

        // Show/hide teacher ID input based on role selected
        roleRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.radio_student) {
                teacherIdContainer.visibility = LinearLayout.VISIBLE
            } else {
                teacherIdContainer.visibility = LinearLayout.GONE
            }
        }

        registerButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val confirmPassword = confirmPasswordEditText.text.toString().trim()
            val selectedRoleId = roleRadioGroup.checkedRadioButtonId

            if (name.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill all required fields!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (selectedRoleId == -1) {
                Toast.makeText(this, "Please select your role!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val role: String
            val teacherId: String?

            when (selectedRoleId) {
                R.id.radio_student -> {
                    role = "Student"
                    teacherId = teacherIdEditText.text.toString().trim()
                    if (teacherId.isEmpty()) {
                        Toast.makeText(this, "Please enter your Teacher ID!", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                }
                R.id.radio_teacher -> {
                    role = "Teacher"
                    teacherId = null // No teacher ID needed
                }
                else -> {
                    Toast.makeText(this, "Invalid role selection", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            registerUser(name, username, email, password, confirmPassword, role, teacherId)
        }

        signInText.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun registerUser(
        name: String,
        username: String,
        email: String,
        password: String,
        confirmPassword: String,
        role: String,
        teacherId: String?
    ) {
        val request = TeacherRegisterRequest(
            name = name,
            username = username,
            email = email,
            password = password,
            confirm_password = confirmPassword,
            role = role,
            teacherId = teacherId
        )

        RetrofitClient.instance.registerUser(request).enqueue(object : retrofit2.Callback<RegisterResponse> {
            override fun onResponse(
                call: retrofit2.Call<RegisterResponse>,
                response: retrofit2.Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    val registerResponse = response.body()
                    if (registerResponse?.status == true) {
                        Toast.makeText(this@TeacherRegisterActivity, registerResponse.message, Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@TeacherRegisterActivity, LoginActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@TeacherRegisterActivity, registerResponse?.message ?: "Registration failed", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@TeacherRegisterActivity, "Server Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: retrofit2.Call<RegisterResponse>, t: Throwable) {
                Toast.makeText(this@TeacherRegisterActivity, "Network Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
