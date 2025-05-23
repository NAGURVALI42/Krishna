package com.example.krishna

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatActivity : AppCompatActivity() {
    private val apiKey = BuildConfig.GEMINI_API_KEY // Secure API Key Storage
    private lateinit var inputQuery: EditText
    private lateinit var btnSend: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var responseAdapter: ResponseAdapter
    private val responseList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        inputQuery = findViewById(R.id.inputQuery)
        btnSend = findViewById(R.id.btnSend)
        recyclerView = findViewById(R.id.recyclerView)

        // Setup RecyclerView
        responseAdapter = ResponseAdapter(responseList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = responseAdapter

        btnSend.setOnClickListener {
            val userQuery = inputQuery.text.toString().trim()
            if (userQuery.isNotEmpty()) {
                fetchGeminiResponse(userQuery)
            } else {
                Toast.makeText(this, "Please enter a query", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchGeminiResponse(query: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://generativelanguage.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(GeminiApiService::class.java)

        // Construct request using correct JSON structure
        val request = GeminiRequest(
            listOf(Content(parts = listOf(Part(text = query))))
        )

        service.getGeminiResponse(request, BuildConfig.GEMINI_API_KEY).enqueue(object : Callback<GeminiResponse> {
            override fun onResponse(call: Call<GeminiResponse>, response: Response<GeminiResponse>) {
                if (response.isSuccessful) {
                    val result = response.body()?.candidates?.get(0)?.content?.parts?.get(0)?.text ?: "No response"
                    responseAdapter.addResponse("User: $query\nGemini: $result")
                } else {
                    val errorBody = response.errorBody()?.string()
                    responseAdapter.addResponse("Error: ${response.code()} - $errorBody")
                }
            }

            override fun onFailure(call: Call<GeminiResponse>, t: Throwable) {
                responseAdapter.addResponse("Failed: ${t.message}")
            }
        })
    }


}
