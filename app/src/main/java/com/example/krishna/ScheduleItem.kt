package com.example.krishna

data class ScheduleItem(
    val id: Int,
    val subject_name: String,
    val schedule_date: String,
    val start_time: String,
    val end_time: String,
    val class_name: String?,   // Optional, include if your API returns it
    val schedule_type: String?, // Optional, include if your API returns it
    val room_no: String?       // Optional
)

data class ScheduleResponse(
    val status: Boolean,
    val message: String?,
    val data: List<ScheduleItem>?
)
