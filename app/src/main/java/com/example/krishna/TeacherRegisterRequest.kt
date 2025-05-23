package com.example.krishna

data class TeacherRegisterRequest(
    val name: String,
    val username: String,
    val email: String,
    val password: String,
    val confirm_password: String,
    val role: String,         // corresponds to user_type in PHP
    val teacherId: String? = null
)
