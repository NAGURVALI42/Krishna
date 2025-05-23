package com.example.krishna

data class WorkListResponse(
    val status: Boolean,
    val message: String,
    val data: List<StudentWorkModel>
)
