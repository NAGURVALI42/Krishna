package com.example.krishna

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class StudentNoticeAdapter(
    private val notices: MutableList<NoticeItem>,
) : RecyclerView.Adapter<StudentNoticeAdapter.NoticeViewHolder>() {

    inner class NoticeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgNotice: ImageView = itemView.findViewById(R.id.imgNotice)
        val tvNoticeTitle: TextView = itemView.findViewById(R.id.tvNoticeTitle)
        val tvNoticeDescription: TextView = itemView.findViewById(R.id.tvNoticeDescription)
        val btnDeleteNotice: ImageButton = itemView.findViewById(R.id.btnDeleteNotice)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.teacher_item_notice, parent, false)
        return NoticeViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoticeViewHolder, position: Int) {
        val notice = notices[position]
//        holder.imgNotice.setImageResource(notice.cover_image)
        holder.tvNoticeTitle.text = notice.description
        holder.tvNoticeDescription.text = notice.description

        holder.btnDeleteNotice.visibility = View.INVISIBLE
    }

    override fun getItemCount(): Int = notices.size

    // Optional: To remove notice from list after delete
    fun removeNotice(position: Int) {
        if (position in notices.indices) {
            notices.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}
