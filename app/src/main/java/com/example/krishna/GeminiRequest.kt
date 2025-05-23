package com.example.krishna

data class GeminiRequest(
    val contents: List<Content> // Matches JSON format
)

data class Content(
    val parts: List<Part>
)

 data class Part(
    val text: String
)

data class GeminiResponse(
    val candidates: List<Candidate>?
)

data class Candidate(
    val content: Content?
)
