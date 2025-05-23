package com.example.krishna

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ChatBotActivity : AppCompatActivity() {

    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var messageInput: EditText
    private lateinit var sendButton: ImageView
    private val chatList = mutableListOf<String>()
    private lateinit var chatAdapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatbot)

        chatRecyclerView = findViewById(R.id.chatListView)
        messageInput = findViewById(R.id.chatInput)
        sendButton = findViewById(R.id.sendButton)

        chatAdapter = ChatAdapter(chatList)
        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatRecyclerView.adapter = chatAdapter

        sendButton.setOnClickListener {
            val message = messageInput.text.toString().trim()
            if (message.isNotEmpty()) {
                chatList.add("You: $message")
                chatAdapter.notifyItemInserted(chatList.size - 1)
                chatRecyclerView.scrollToPosition(chatList.size - 1)
                messageInput.setText("")
                
                // Simulating a bot response
                chatList.add("Bot: I'm here to help!")
                chatAdapter.notifyItemInserted(chatList.size - 1)
                chatRecyclerView.scrollToPosition(chatList.size - 1)
            }
        }
    }
}
