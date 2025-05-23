package com.example.krishna

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.content.Context

class TimeTableAdapter(
    private var timeTableList: MutableList<ScheduleItem>,
    private val context: Context
) : RecyclerView.Adapter<TimeTableAdapter.TimeTableViewHolder>() {

    class TimeTableViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val subjectTextView: TextView = itemView.findViewById(R.id.tvSubject)
        val dateTimeTextView: TextView = itemView.findViewById(R.id.tvDateTime)
        val deleteButton: Button = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeTableViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.teacher_item_timetable, parent, false)
        return TimeTableViewHolder(view)
    }

    override fun onBindViewHolder(holder: TimeTableViewHolder, position: Int) {
        val item = timeTableList[position]
        holder.subjectTextView.text = item.subject_name
        holder.dateTimeTextView.text =
            "${item.schedule_date} -> ${item.start_time} to ${item.end_time}"

        holder.deleteButton.setOnClickListener {
//            RetrofitClient.instance.deleteSchedule(item.id).enqueue(object : Callback<DeleteResponse> {
//                override fun onResponse(call: Call<DeleteResponse>, response: Response<DeleteResponse>) {
//                    if (response.body()?.status == true) {
//                        timeTableList.removeAt(position)
//                        notifyItemRemoved(position)
//                        Toast.makeText(context, "Deleted successfully", Toast.LENGTH_SHORT).show()
//                    } else {
//                        Toast.makeText(context, "Delete failed", Toast.LENGTH_SHORT).show()
//                    }
//                }
//
//                override fun onFailure(call: Call<DeleteResponse>, t: Throwable) {
//                    Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
//                }
//            })
        }
    }

    override fun getItemCount(): Int = timeTableList.size
}
