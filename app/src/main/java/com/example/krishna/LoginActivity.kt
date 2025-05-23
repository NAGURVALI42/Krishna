package com.example.krishna

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val usernameEditText = findViewById<EditText>(R.id.username)
        val passwordEditText = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.btn_login)
        val registerText = findViewById<TextView>(R.id.register_text)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter both username and password!", Toast.LENGTH_SHORT).show()
            } else {
                val loginRequest = LoginRequest(username, password)

                RetrofitClient.instance.loginUser(loginRequest).enqueue(object : retrofit2.Callback<LoginResponse> {
                    override fun onResponse(call: Call<LoginResponse>, response: retrofit2.Response<LoginResponse>) {
                        if (response.isSuccessful && response.body()?.success == true) {
                            val user = response.body()?.user
                            runOnUiThread {

                                if (user?.user_type.equals("Student", ignoreCase = true)){
                                    Toast.makeText(this@LoginActivity, "Welcome ${user?.username}", Toast.LENGTH_SHORT).show()

                                    val sf = getSharedPreferences("APPSF", MODE_PRIVATE)
                                    sf.edit().putInt("USER_ID",user?.id!!).apply()
                                    sf.edit().putString("NAME",user?.name!!).apply()
                                    sf.edit().putString("USER_EMAIL",user?.email!!).apply()
                                    sf.edit().putString("USER_TYPE",user?.user_type!!).apply()
                                    // Navigate to home/dashboard activity
                                    val intent = Intent(this@LoginActivity, StudentDashboard::class.java)
                                    startActivity(intent)
                                    finish()
                                } else if (user?.user_type.equals("Teacher", ignoreCase = true)){
                                    Toast.makeText(this@LoginActivity, "Welcome ${user?.username}", Toast.LENGTH_SHORT).show()

                                    val sf = getSharedPreferences("APPSF", MODE_PRIVATE)
                                    sf.edit().putInt("USER_ID",user?.id!!).apply()
                                    sf.edit().putString("NAME",user?.name!!).apply()
                                    sf.edit().putString("USER_EMAIL",user?.email!!).apply()
                                    sf.edit().putString("USER_TYPE",user?.user_type!!).apply()
                                    // Navigate to home/dashboard activity
                                    val intent = Intent(this@LoginActivity, TeacherDashboard::class.java)
                                    startActivity(intent)
                                    finish()
                                }

                            }
                        } else {
                            // Handle API error response
                            runOnUiThread {
                                Toast.makeText(this@LoginActivity, "Invalid credentials!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        runOnUiThread {
                            Toast.makeText(this@LoginActivity, "Login failed: ${t.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }
        }


        registerText.setOnClickListener {
            startActivity(Intent(this, TeacherRegisterActivity::class.java))
        }
    }


}
