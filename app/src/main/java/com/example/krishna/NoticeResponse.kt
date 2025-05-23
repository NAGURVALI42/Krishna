package com.example.krishna

data class NoticeResponse(
    val status: Boolean,
    val message: String,
    val data: Data?
) {
    data class Data(
        val notice_id: Int,
        val cover_image: String
    )
}
