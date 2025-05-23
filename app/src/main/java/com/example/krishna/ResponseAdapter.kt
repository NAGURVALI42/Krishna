package com.example.krishna

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ResponseAdapter(private val responses: ArrayList<String>) :
    RecyclerView.Adapter<ResponseAdapter.ResponseViewHolder>() {

    class ResponseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val responseText: TextView = itemView.findViewById(R.id.responseText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResponseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.teacher_item_response, parent, false)
        return ResponseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResponseViewHolder, position: Int) {
        holder.responseText.text = responses[position]
    }

    override fun getItemCount(): Int {
        return responses.size
    }

    fun addResponse(response: String) {
        responses.add(response)
        notifyItemInserted(responses.size - 1)
    }
}
