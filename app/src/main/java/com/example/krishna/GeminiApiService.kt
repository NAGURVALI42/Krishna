package com.example.krishna

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query


interface GeminiApiService {
    @Headers("com.example.krishna.Content-Type: application/json")
    @POST("v1beta/models/gemini-2.0-flash:generateContent") // Use Gemini-2 Flash
    fun getGeminiResponse(
        @Body request: GeminiRequest,
        @Query("key") apiKey: String
    ): Call<GeminiResponse>

}

