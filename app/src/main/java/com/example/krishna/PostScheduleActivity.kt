package com.example.krishna

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostScheduleActivity : AppCompatActivity() {

    private lateinit var etClassName: EditText
    private lateinit var etSubject: EditText
    private lateinit var etDate: EditText
    private lateinit var etStartTime: EditText
    private lateinit var etEndTime: EditText
    private lateinit var etRoom: EditText
    private lateinit var btnPost: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_schedule)

        etClassName = findViewById(R.id.etClassName)
        etSubject = findViewById(R.id.etSubject)
        etDate = findViewById(R.id.etDate)
        etStartTime = findViewById(R.id.etStartTime)
        etEndTime = findViewById(R.id.etEndTime)
        etRoom = findViewById(R.id.etRoom)
        btnPost = findViewById(R.id.btnPost)

        btnPost.setOnClickListener {
            postSchedule()
        }
    }

    private fun postSchedule() {
        val className = etClassName.text.toString().trim()
        val subject = etSubject.text.toString().trim()
        val date = etDate.text.toString().trim()
        val startTime = etStartTime.text.toString().trim()
        val endTime = etEndTime.text.toString().trim()
        val room = etRoom.text.toString().trim()

        if (className.isEmpty() || subject.isEmpty() || date.isEmpty() ||
            startTime.isEmpty() || endTime.isEmpty() || room.isEmpty()
        ) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val request = PostScheduleRequest(
            schedule_type = "exam",
            class_name = className,
            subject_name = subject,
            schedule_date = date,
            start_time = startTime,
            end_time = endTime,
            room_no = room
        )

        RetrofitClient.instance.postSchedule(request)
            .enqueue(object : Callback<PostScheduleResponse> {
                override fun onResponse(
                    call: Call<PostScheduleResponse>,
                    response: Response<PostScheduleResponse>
                ) {
                    if (response.isSuccessful && response.body()?.status == true) {
                        Toast.makeText(this@PostScheduleActivity, "Schedule posted successfully", Toast.LENGTH_SHORT).show()
                        finish() // go back to previous screen
                    } else {
                        Toast.makeText(this@PostScheduleActivity, "Failed: ${response.body()?.message}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<PostScheduleResponse>, t: Throwable) {
                    Toast.makeText(this@PostScheduleActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
