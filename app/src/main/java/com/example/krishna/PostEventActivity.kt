package com.example.krishna

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.util.*

class PostEventActivity : AppCompatActivity() {

    private lateinit var etEventDate: EditText
    private lateinit var etDueDate: EditText
    private lateinit var etDescription: EditText
    private lateinit var btnSelectImage: Button
    private lateinit var ivSelectedImage: ImageView
    private lateinit var btnSubmitEvent: Button

    private var selectedImageUri: Uri? = null

    companion object {
        private const val REQUEST_CODE_PICK_IMAGE = 2001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_event_post)

        etEventDate = findViewById(R.id.etNoticeDate)
        etDueDate = findViewById(R.id.etDueDate)
        etDescription = findViewById(R.id.etDescription)
        btnSelectImage = findViewById(R.id.btnSelectImage)
        ivSelectedImage = findViewById(R.id.ivSelectedImage)
        btnSubmitEvent = findViewById(R.id.btnSubmitNotice)

        btnSelectImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE)
        }

        btnSubmitEvent.setOnClickListener {
            submitEvent()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            selectedImageUri = data?.data
            ivSelectedImage.setImageURI(selectedImageUri)
            ivSelectedImage.visibility = ImageView.VISIBLE
        }
    }

    private fun uriToFile(uri: Uri): File? {
        val inputStream = contentResolver.openInputStream(uri) ?: return null
        val file = File(cacheDir, UUID.randomUUID().toString() + ".jpg")
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)
        outputStream.close()
        inputStream.close()
        return file
    }

    private fun submitEvent() {
        val eventDate = etEventDate.text.toString().trim()
        val dueDate = etDueDate.text.toString().trim()
        val description = etDescription.text.toString().trim()

        if (eventDate.isEmpty() || dueDate.isEmpty() || description.isEmpty() || selectedImageUri == null) {
            Toast.makeText(this, "All fields and image required", Toast.LENGTH_SHORT).show()
            return
        }

        val imageFile = uriToFile(selectedImageUri!!)
        if (imageFile == null) {
            Toast.makeText(this, "Invalid image file", Toast.LENGTH_SHORT).show()
            return
        }

        val requestBodyEventDate = eventDate.toRequestBody("text/plain".toMediaTypeOrNull())
        val requestBodyDueDate = dueDate.toRequestBody("text/plain".toMediaTypeOrNull())
        val requestBodyDescription = description.toRequestBody("text/plain".toMediaTypeOrNull())

        val imageRequestBody = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
        val multipartImage = MultipartBody.Part.createFormData("cover_image", imageFile.name, imageRequestBody)

        val call = RetrofitClient.instance.postEvent(
            requestBodyEventDate,
            requestBodyDueDate,
            requestBodyDescription,
            multipartImage
        )

        call.enqueue(object : Callback<PostEventResponse> {
            override fun onResponse(call: Call<PostEventResponse>, response: Response<PostEventResponse>) {
                if (response.isSuccessful && response.body()?.status == true) {
                    Toast.makeText(this@PostEventActivity, "Event posted successfully", Toast.LENGTH_LONG).show()
                    clearFields()
                } else {
                    Toast.makeText(this@PostEventActivity, "Error: ${response.body()?.message}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<PostEventResponse>, t: Throwable) {
                Toast.makeText(this@PostEventActivity, "Failure: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun clearFields() {
        etEventDate.text.clear()
        etDueDate.text.clear()
        etDescription.text.clear()
        ivSelectedImage.setImageURI(null)
        ivSelectedImage.visibility = ImageView.GONE
        selectedImageUri = null
    }
}
