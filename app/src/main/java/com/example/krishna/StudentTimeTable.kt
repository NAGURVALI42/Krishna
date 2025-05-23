package com.example.krishna

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StudentTimeTable : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TimeTableAdapter
    private lateinit var postButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_time_table)

        recyclerView = findViewById(R.id.recyclerViewTimeTable)
        postButton = findViewById(R.id.btnAddSchedule)
        recyclerView.layoutManager = LinearLayoutManager(this)

        postButton.setOnClickListener {
            val intent = Intent(this@StudentTimeTable,PostScheduleActivity::class.java)
            startActivity(intent)
        }



        loadSchedule()
    }

    private fun loadSchedule() {
        RetrofitClient.instance.getSchedules().enqueue(object : Callback<ScheduleResponse> {
            override fun onResponse(call: Call<ScheduleResponse>, response: Response<ScheduleResponse>) {
                if (response.body()?.status == true && response.body()?.data != null) {
                    adapter = TimeTableAdapter(response.body()!!.data!!.toMutableList(), this@StudentTimeTable)
                    recyclerView.adapter = adapter
                } else {
                    Toast.makeText(this@StudentTimeTable, "No schedule found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ScheduleResponse>, t: Throwable) {
                Toast.makeText(this@StudentTimeTable, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
