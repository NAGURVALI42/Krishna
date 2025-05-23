package com.example.krishna

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeacherAchievementsActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AchievementsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.teacher_activity_achievements)

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
                        Toast.makeText(this@TeacherAchievementsActivity, "No achievements found", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<AchievementResponse>, t: Throwable) {
                    Toast.makeText(this@TeacherAchievementsActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
