package com.example.krishna



data class NoticeListResponse(
    val status: Boolean,
    val message: String,
    val data: List<NoticeItem>
)


data class NoticeItem(
    val id: String,
    val notice_date: String,
    val due_date: String,
    val description: String,
    val link_url: String,
    val cover_image: String
)
