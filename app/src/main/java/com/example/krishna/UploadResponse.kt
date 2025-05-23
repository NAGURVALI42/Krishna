package com.example.krishna

data class UploadResponse(
    val status: Boolean,
    val message: String,
    val data: UploadData?
)

data class UploadData(
    val work_id: Int
)
