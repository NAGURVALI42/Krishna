package com.example.krishna

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeacherNoticesActivity : AppCompatActivity() {

    private lateinit var noticesList: MutableList<NoticeItem>
    private lateinit var adapter: NoticeAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var backButton: ImageView
    private lateinit var postNoticeBtn: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.teacher_activity_notices)

        backButton = findViewById(R.id.back_button)
        postNoticeBtn = findViewById(R.id.btnAddNotice)
        recyclerView = findViewById(R.id.recyclerViewNotices)

        backButton.setOnClickListener {
            finish()
        }

        postNoticeBtn.setOnClickListener {
            startActivity(Intent(this@TeacherNoticesActivity, PostEventActivity::class.java))
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        noticesList = mutableListOf()
        adapter = NoticeAdapter(noticesList) { position ->
            // Handle delete notice click
            noticesList.removeAt(position)
            adapter.notifyItemRemoved(position)
            Toast.makeText(this, "Notice deleted", Toast.LENGTH_SHORT).show()
        }
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
                    Toast.makeText(this@TeacherNoticesActivity, "Failed to retrieve notices", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<NoticeListResponse>, t: Throwable) {
                Toast.makeText(this@TeacherNoticesActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
