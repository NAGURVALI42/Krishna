package com.example.krishna

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.krishna.adapter.TeacherDocumentAdapter
import com.example.krishna.model.TeacherDocumentModel

class StudentWork : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TeacherDocumentAdapter
    private val documentList = mutableListOf<TeacherDocumentModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_work)

        recyclerView = findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = TeacherDocumentAdapter(documentList) { position ->
            documentList.removeAt(position)
            adapter.notifyItemRemoved(position)
        }
        recyclerView.adapter = adapter

        // You can fetch and display already uploaded documents here if needed
    }
}
