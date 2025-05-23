package com.example.krishna

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AchievementsAdapter(
    private val achievementsList: List<Achievement>
) : RecyclerView.Adapter<AchievementsAdapter.AchievementViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchievementViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_achievement, parent, false)
        return AchievementViewHolder(view)
    }

    override fun onBindViewHolder(holder: AchievementViewHolder, position: Int) {
        val achievement = achievementsList[position]
        holder.title.text = achievement.award_name
        holder.description.text = achievement.description
    }

    override fun getItemCount(): Int = achievementsList.size

    class AchievementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvAchievementTitle)
        val description: TextView = itemView.findViewById(R.id.tvAchievementDescription)
    }
}
