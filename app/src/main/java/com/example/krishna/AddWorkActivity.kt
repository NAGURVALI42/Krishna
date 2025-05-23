package com.example.krishna

import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class AddWorkActivity : AppCompatActivity() {

    private lateinit var editTextDate: EditText
    private lateinit var editTextDueDate: EditText
    private lateinit var editTextUploaderId: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var textViewFileName: TextView
    private var selectedFileUri: Uri? = null

    private val filePickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            selectedFileUri = it
            textViewFileName.text = getFileName(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_work)

        editTextDate = findViewById(R.id.editTextDate)
        editTextDueDate = findViewById(R.id.editTextDueDate)
        editTextUploaderId = findViewById(R.id.editTextUploaderId)
        editTextDescription = findViewById(R.id.editTextDescription)
        textViewFileName = findViewById(R.id.textViewFileName)
        val buttonSelectFile = findViewById<Button>(R.id.buttonSelectFile)
        val buttonUpload = findViewById<Button>(R.id.buttonUpload)

        buttonSelectFile.setOnClickListener {
            filePickerLauncher.launch("application/pdf")
        }

        buttonUpload.setOnClickListener {
            if (selectedFileUri == null) {
                Toast.makeText(this, "Please select a document", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val date = editTextDate.text.toString().trim()
            val dueDate = editTextDueDate.text.toString().trim()
            val uploaderId = editTextUploaderId.text.toString().trim()
            val description = editTextDescription.text.toString().trim()

            if (date.isEmpty() || dueDate.isEmpty() || uploaderId.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            uploadWork(selectedFileUri!!, date, dueDate, uploaderId, description)
        }
    }

    private fun uploadWork(uri: Uri, date: String, dueDate: String, uploaderId: String, description: String) {
        val fileName = getFileName(uri)
        val file = File(cacheDir, fileName)

        try {
            contentResolver.openInputStream(uri)?.use { inputStream ->
                file.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Failed to read file: ${e.message}", Toast.LENGTH_SHORT).show()
            return
        }

        val requestFile = RequestBody.create("application/pdf".toMediaTypeOrNull(), file)
        val documentPart = MultipartBody.Part.createFormData("document", file.name, requestFile)

        val datePart = RequestBody.create("text/plain".toMediaTypeOrNull(), date)
        val dueDatePart = RequestBody.create("text/plain".toMediaTypeOrNull(), dueDate)
        val uploaderIdPart = RequestBody.create("text/plain".toMediaTypeOrNull(), uploaderId)
        val descriptionPart = RequestBody.create("text/plain".toMediaTypeOrNull(), description)

        RetrofitClient.instance.uploadWork(datePart, dueDatePart, uploaderIdPart, descriptionPart, documentPart)
            .enqueue(object : Callback<UploadResponse> {
                override fun onResponse(call: Call<UploadResponse>, response: Response<UploadResponse>) {
                    if (response.isSuccessful && response.body()?.status == true) {
                        Toast.makeText(this@AddWorkActivity, "Uploaded Successfully", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@AddWorkActivity, "Upload failed: ${response.body()?.message ?: "Unknown error"}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                    Toast.makeText(this@AddWorkActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun getFileName(uri: Uri): String {
        var result = "document.pdf"
        contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (index != -1) {
                    result = cursor.getString(index)
                }
            }
        }
        return result
    }
}
