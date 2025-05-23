package com.example.krishna

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.krishna.adapters.TeacherEventAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeacherEventsActivity : AppCompatActivity() {

    private lateinit var eventAdapter: TeacherEventAdapter
    private val eventList = mutableListOf<TeacherEventModel>()

    lateinit var recyclerView: RecyclerView
    lateinit var fabAddEvent: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.teacher_activity_events)

        recyclerView = findViewById(R.id.recyclerView)
        fabAddEvent = findViewById(R.id.fabAddEvent)

        eventAdapter = TeacherEventAdapter(eventList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = eventAdapter

        fabAddEvent.setOnClickListener {
            startActivity(Intent(this, PostEventActivity::class.java))
        }

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
                    Toast.makeText(this@TeacherEventsActivity, "Failed: ${response.body()?.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GetEventsResponse>, t: Throwable) {
                Toast.makeText(this@TeacherEventsActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onResume() {
        super.onResume()

        fetchEvents()
    }
}
