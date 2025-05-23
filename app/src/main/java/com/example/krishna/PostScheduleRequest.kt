package com.example.krishna

data class PostScheduleRequest(
    val schedule_type: String,
    val class_name: String,
    val subject_name: String,
    val schedule_date: String,
    val start_time: String,
    val end_time: String,
    val room_no: String
)

data class PostScheduleResponse(
    val status: Boolean,
    val message: String,
    val data: Data?
)

//class Data(val id: Int)
