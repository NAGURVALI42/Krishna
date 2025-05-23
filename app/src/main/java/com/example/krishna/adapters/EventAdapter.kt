package com.example.krishna.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.krishna.R
import com.example.krishna.TeacherEventModel

class EventAdapter(
    private val eventList: List<TeacherEventModel>,
    private val onItemClick: (TeacherEventModel) -> Unit
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val eventTitle: TextView = view.findViewById(R.id.eventTitle)
        val eventImage: ImageView = view.findViewById(R.id.eventImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.teacher_item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = eventList[position]
        holder.eventTitle.text = event.description

        // Load image using Glide
        Glide.with(holder.itemView.context)
            .load(event.cover_image)
            .placeholder(R.drawable.event_pic)
            .into(holder.eventImage)

        holder.itemView.setOnClickListener { onItemClick(event) }
    }

    override fun getItemCount(): Int = eventList.size
}
