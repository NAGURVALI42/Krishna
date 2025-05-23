package com.example.krishna

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class TeacherAddEventActivity : AppCompatActivity() {

    private var selectedImageUri: Uri? = null
    lateinit var eventImage:ImageView
    lateinit var btnSaveEvent:Button
    lateinit var inputEventTitle:EditText
    lateinit var inputEventDescription:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.teacher_item_event)

        eventImage = findViewById(R.id.eventImage)

        // Select Image
        eventImage.setOnClickListener {
            openImagePicker()
        }

        // Save Event
        btnSaveEvent.setOnClickListener {
            val title = inputEventTitle.text.toString().trim()
            val description = inputEventDescription.text.toString().trim()

            if (title.isEmpty() || description.isEmpty() || selectedImageUri == null) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
//                saveEvent(title, description, selectedImageUri.toString())
            }
        }
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_IMAGE_PICK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK) {
            selectedImageUri = data?.data
            eventImage.setImageURI(selectedImageUri)
        }
    }

    companion object {
        private const val REQUEST_IMAGE_PICK = 100
    }
}
