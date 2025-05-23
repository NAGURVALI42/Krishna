package com.example.krishna

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.krishna.adapters.TeacherEventAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StudentEvent : AppCompatActivity() {
    private lateinit var eventAdapter: TeacherEventAdapter
    private val eventList = mutableListOf<TeacherEventModel>()

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_event)

        recyclerView = findViewById(R.id.recyclerView)

        eventAdapter = TeacherEventAdapter(eventList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = eventAdapter

        fetchEvents()
    }

    private fun fetchEvents() {
        RetrofitClient.instance.getEvents().enqueue(object : Callback<GetEventsResponse> {
            override fun onResponse(call: Call<GetEventsResponse>, response: Response<GetEventsResponse>) {
                if (response.isSuccessful && response.body()?.status == true) {
                    eventList.clear()
                    response.body()?.data?.let { eventList.addAll(it) }
                    eventAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this@StudentEvent, "Failed: ${response.body()?.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GetEventsResponse>, t: Throwable) {
                Toast.makeText(this@StudentEvent, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        fetchEvents()
    }
}
