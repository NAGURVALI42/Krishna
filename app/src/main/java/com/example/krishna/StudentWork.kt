package com.example.krishna

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StudentWork : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StudentWorkAdapter
    private val documentList = mutableListOf<StudentWorkModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_work)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = StudentWorkAdapter(documentList)
        recyclerView.adapter = adapter
        fetchWorkItems()
    }

    private fun fetchWorkItems() {
        RetrofitClient.instance.getWork().enqueue(object : Callback<WorkListResponse> {
            override fun onResponse(call: Call<WorkListResponse>, response: Response<WorkListResponse>) {
                if (response.isSuccessful && response.body()?.status == true) {
                    val workItems = response.body()?.data ?: emptyList()
                    Log.d("TAG", "Response: $workItems")
                    documentList.addAll(workItems)
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this@StudentWork, "Failed to load work", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<WorkListResponse>, t: Throwable) {
                Toast.makeText(this@StudentWork, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
