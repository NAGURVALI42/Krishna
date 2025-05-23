package com.example.krishna

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StudentAchievement : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AchievementsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_achievement)

        recyclerView = findViewById(R.id.recyclerViewAchievements)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchAchievements()
    }

    private fun fetchAchievements() {
        val achieverId = getSharedPreferences("APPSF", MODE_PRIVATE).getInt("USER_ID",-1) // Replace with dynamic value if needed
        RetrofitClient.instance.getAchievements(achieverId)
            .enqueue(object : Callback<AchievementResponse> {
                override fun onResponse(call: Call<AchievementResponse>, response: Response<AchievementResponse>) {
                    if (response.body()?.status == true && response.body()?.data != null) {
                        adapter = AchievementsAdapter(response.body()!!.data!!)
                        recyclerView.adapter = adapter
                    } else {
                        Toast.makeText(this@StudentAchievement, "No achievements found", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<AchievementResponse>, t: Throwable) {
                    Toast.makeText(this@StudentAchievement, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
