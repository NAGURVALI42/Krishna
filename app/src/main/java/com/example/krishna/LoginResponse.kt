package com.example.krishna

data class LoginResponse(
    val success: Boolean,
    val user: User?
)

data class ErrorResponse(
    val error: String
)
