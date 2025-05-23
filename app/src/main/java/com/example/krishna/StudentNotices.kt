package com.example.krishna

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StudentNotices : AppCompatActivity() {

    private lateinit var noticesList: MutableList<NoticeItem>
    private lateinit var adapter: StudentNoticeAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var backButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_notices)

        backButton = findViewById(R.id.back_button)
        recyclerView = findViewById(R.id.recyclerViewNotices)

        backButton.setOnClickListener {
            finish()
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        noticesList = mutableListOf()

        // Adapter without delete click listener
        adapter = StudentNoticeAdapter(noticesList)
        recyclerView.adapter = adapter

        fetchNotices()
    }

    private fun fetchNotices() {
        val call = RetrofitClient.instance.getNotices()
        call.enqueue(object : Callback<NoticeListResponse> {
            override fun onResponse(call: Call<NoticeListResponse>, response: Response<NoticeListResponse>) {
                if (response.isSuccessful && response.body()?.status == true) {
                    response.body()?.data?.let {
                        noticesList.clear()
                        noticesList.addAll(it)
                        adapter.notifyDataSetChanged()
                    }
                } else {
                    Toast.makeText(this@StudentNotices, "Failed to retrieve notices", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<NoticeListResponse>, t: Throwable) {
                Toast.makeText(this@StudentNotices, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
