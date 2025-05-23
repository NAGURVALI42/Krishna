package com.example.krishna

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TeacherworkActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TeacherDocumentAdapter
    private val documentList = mutableListOf<TeacherDocumentModel>()
    private lateinit var fabAddWork: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.teacher_activity_work)

        recyclerView = findViewById(R.id.recyclerView)
        fabAddWork = findViewById(R.id.fabAddWork)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = TeacherDocumentAdapter(documentList) { position ->
            documentList.removeAt(position)
            adapter.notifyItemRemoved(position)
        }
        recyclerView.adapter = adapter

        // FAB now only opens the AddWorkActivity screen:
        fabAddWork.setOnClickListener {
            val intent = Intent(this, AddWorkActivity::class.java)
            startActivity(intent)
        }

        // Load any initial work data if needed
        loadInitialWorkItems()
    }

    private fun loadInitialWorkItems() {
        // Example data; replace with your data source
        documentList.add(TeacherDocumentModel("XI-Subject", "Homework1.pdf"))
        documentList.add(TeacherDocumentModel("XI-Subject", "Assignment2.pdf"))
        adapter.notifyDataSetChanged()
    }
}
