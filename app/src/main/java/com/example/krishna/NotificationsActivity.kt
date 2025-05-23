package com.example.krishna

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NotificationsActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NotificationsAdapter
    private val notificationsList = listOf(
        "Exam schedule updated!",
        "New assignment added.",
        "School will be closed on Friday.",
        "Reminder: Submit your project by Monday."
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)

        recyclerView = findViewById(R.id.notificationsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = NotificationsAdapter(notificationsList)
        recyclerView.adapter = adapter
    }
}
