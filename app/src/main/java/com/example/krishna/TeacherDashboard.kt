package com.example.krishna

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class TeacherDashboard : AppCompatActivity() {
    private lateinit var name: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.teacher_activity_dashboard)


        // Find the "Notices" section and set a click listener
        val noticesSection: LinearLayout = findViewById(R.id.notices_section)
        noticesSection.setOnClickListener {
            val intent = Intent(this, TeacherNoticesActivity::class.java)
            startActivity(intent)
        }

        val eventLayout: LinearLayout = findViewById(R.id.event_layout)
        eventLayout.setOnClickListener {
            val intent = Intent(this, TeacherEventsActivity::class.java)
            startActivity(intent)
        }

        val addWork: LinearLayout = findViewById(R.id.add_work)
        addWork.setOnClickListener {
            val intent = Intent(this, TeacherworkActivity::class.java)
            startActivity(intent)
        }
        val timetable: LinearLayout = findViewById(R.id.time_table)
        timetable.setOnClickListener {
            val intent = Intent(this, TeacherTimeTableActivity::class.java)
            startActivity(intent)
        }
        val Achiev: LinearLayout = findViewById(R.id.achiev)
        Achiev.setOnClickListener {
            val intent = Intent(this, TeacherAchievementsActivity::class.java)
            startActivity(intent)
        }
        val search: LinearLayout = findViewById(R.id.search_bar)
        search.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)
            startActivity(intent)
        }
        val setting: LinearLayout = findViewById(R.id.setting)
        setting.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        val img: ImageView = findViewById(R.id.profile_img)
        img.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        name = findViewById(R.id.name)
        val Vname = getSharedPreferences("APPSF", MODE_PRIVATE).getString("NAME","Vivek")
        name.text=Vname;

    }
}
