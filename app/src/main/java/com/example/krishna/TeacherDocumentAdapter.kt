package com.example.krishna.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.krishna.R
import com.example.krishna.model.TeacherDocumentModel

class TeacherDocumentAdapter(
    private val documents: MutableList<TeacherDocumentModel>,
    private val onDeleteClick: (Int) -> Unit
) : RecyclerView.Adapter<TeacherDocumentAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val subjectText: TextView = view.findViewById(R.id.subjectText)
        val fileNameText: TextView = view.findViewById(R.id.fileNameText)
        val deleteBtn: ImageButton = view.findViewById(R.id.deleteBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_teacher_document, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = documents.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val document = documents[position]
        holder.subjectText.text = document.subject
        holder.fileNameText.text = document.fileName
        holder.deleteBtn.setOnClickListener {
            onDeleteClick(position)
        }
    }
}
