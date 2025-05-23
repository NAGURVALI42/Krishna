package com.example.krishna

data class GetEventsResponse(
    val status: Boolean,
    val message: String,
    val data: List<TeacherEventModel>
)
