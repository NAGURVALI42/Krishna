package com.example.krishna.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.krishna.R
import com.example.krishna.RetrofitClient.IMAGE_URL
import com.example.krishna.TeacherEventModel

class TeacherEventAdapter(private val events: List<TeacherEventModel>) :
    RecyclerView.Adapter<TeacherEventAdapter.EventViewHolder>() {

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDate: TextView = itemView.findViewById(R.id.tvEventDate)
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        val ivCoverImage: ImageView = itemView.findViewById(R.id.ivCoverImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.teacher_item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.tvDate.text = event.event_date
        holder.tvDescription.text = event.description

        val imageUrl = IMAGE_URL + event.cover_image

        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .into(holder.ivCoverImage)
    }

    override fun getItemCount(): Int = events.size
}
