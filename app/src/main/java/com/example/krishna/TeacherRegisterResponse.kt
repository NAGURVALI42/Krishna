package com.example.krishna

data class RegisterResponse(
    val status: Boolean,
    val message: String,
    val data: Data?
)

data class Data(
    val id: Int?
)
