package com.example.krishna

data class PostEventResponse(
    val status: Boolean,
    val message: String,
    val data: EventData?
)

data class EventData(
    val event_id: Int
)
