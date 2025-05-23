package com.example.krishna

data class AchievementResponse(
    val status: Boolean,
    val message: String,
    val data: List<Achievement>?
)
