package com.example.dearfutureme.Activities

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dearfutureme.API.RetrofitInstance
import com.example.dearfutureme.Adapter.ImageAdapter
import com.example.dearfutureme.Model.Capsules
import com.example.dearfutureme.Model.EditCapsuleResponse
import com.example.dearfutureme.Model.Image
import com.example.dearfutureme.Model.Images
import com.example.dearfutureme.R
import com.example.dearfutureme.ViewModel.ImageViewModel
import com.example.dearfutureme.databinding.ActivityCreateCapsuleBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar

class CreateCapsule : AppCompatActivity() {

    lateinit var binding: ActivityCreateCapsuleBinding
    private lateinit var mode: String
    var capsule: Capsules? = null
    private lateinit var imageAdapter: ImageAdapter
    private val imagesList = mutableListOf<Image>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateCapsuleBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        // Initialize RecyclerView
        imageAdapter = ImageAdapter(imagesList) // Use your adapter that displays image filenames
        binding.recyclerViewImage.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewImage.adapter = imageAdapter

        mode = intent.getStringExtra("MODE").toString()
        capsule = intent.getParcelableExtra("CAPSULE")

        sendBtn()
        backBtn()
        setDate()
        addImage()
        setTime()


        if(mode == "EDIT"){
            editBtn()
        } else {
            createBtn()
        }
    }
    private fun addImage() {
        binding.addImageBtn.setOnClickListener {
            pickImageFromGallery()
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    private val IMAGE_PICK_CODE = 1000


    // Function to get the file name from the URI
    private fun getFileName(uri: Uri): String? {
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            val nameIndex = it.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
            if (it.moveToFirst()) {
                return it.getString(nameIndex)
            }
        }
        return null
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == IMAGE_PICK_CODE && resultCode == Activity.RESULT_OK) {
            val selectedImageUri = data?.data
            if (selectedImageUri != null) {
                // Optionally, get the file name
                val fileName = getFileName(selectedImageUri)
                Log.d("Image Selection", "File Name: $fileName")

                // Create your Image object
                val image = Image(
                    id = 0,
                    image = selectedImageUri.toString(),
                    capsuleId = 0,
                    capsuleType = "default"
                )
                imagesList.add(image) // Add to your image list
                imageAdapter.notifyItemInserted(imagesList.size - 1) // Notify the adapter
            }
    }
}


private fun setTime() {
        val timeEditText: EditText = binding.timeSchedule

        timeEditText.setOnClickListener {
            // Get the current time
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)
            val second = calendar.get(Calendar.SECOND)

            // Create and show TimePickerDialog
            val timePickerDialog = TimePickerDialog(
                this,
                { _, selectedHour, selectedMinute ->
                    // Format and set the selected time in HH:MM format
                    val formattedTime = String.format("%02d:%02d:%02d", selectedHour, selectedMinute, second)
                    timeEditText.setText(formattedTime) // Set the time in EditText
                },
                hour, minute, true // true for 24-hour format
            )

            timePickerDialog.show() // Show the time picker dialog
        }
    }

    private fun editBtn() {
        binding.tvMyCapsule.text = "Edit Capsule"
        val title =binding.etTitle.setText(capsule?.title).toString()
        val message = binding.etMessage.setText(capsule?.message).toString()
        val receiver_email = binding.receiverEmail.setText(capsule?.receiverEmail).toString()


        binding.draftBtn.setOnClickListener{
            val request = capsule?.let { it1 -> Capsules(it1.id, title, message, receiver_email, null, null, null) }
            if (request != null) {
                capsule?.let { it1 ->
                    RetrofitInstance.instance.updateCapsule(it1.id, request).enqueue(object : Callback<Capsules>{
                        override fun onResponse(call: Call<Capsules>, response: Response<Capsules>) {
//                            val editResponse = response.body()?.updateMessage
                            if(response.isSuccessful){
                                val capsule = response.body()?.title
                                Log.d("CapsuleUpdate", "Capsule updated successfully: $capsule")
    //                            Toast.makeText(this@CreateCapsule, editResponse, Toast.LENGTH_SHORT).show()
                                displayName()
                            } else {
                                Log.e("CapsuleUpdate", "Error: Response unsuccessful")
                            }
                        }

                        override fun onFailure(call: Call<Capsules>, t: Throwable) {
                            Log.e("Update Error", "Error: ${t.message}")
                        }
                    })
                }
            }
        }
    }

    private fun setDate() {
        val editTextDate = binding.dateSchedule
        editTextDate.setOnClickListener {
            // Get current date
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            // Show DatePickerDialog
            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, monthOfYear, dayOfMonth ->
                    // Format date as dd/MM/yyyy
                    val selectedDate = "$year-${monthOfYear + 1}-$dayOfMonth"
                    editTextDate.setText(selectedDate)  // Set date to EditText
                },
                year, month, day
            )
            datePickerDialog.datePicker.minDate = calendar.timeInMillis

            datePickerDialog.show()
        }
    }

    private fun backBtn() {
        binding.btnBack.setOnClickListener {
            val intent = Intent()
            intent.putExtra("USERNAME", intent.getStringExtra("USERNAME")) // Pass the username back
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun createBtn() {
        binding.draftBtn.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val message = binding.etMessage.text.toString()
            val date = binding.dateSchedule.text.toString()
            val time = binding.timeSchedule.text.toString()
            val receiverEmail = binding.receiverEmail.text.toString()

            if (title.isNotEmpty() && message.isNotEmpty() && date.isNotEmpty()) {
                Log.d("CreateCapsule", "Title: $title, Message: $message, Date: $date, Time: $time")

                // Serialize images as needed
//                val imageUris = imagesList.map { it.image } // Assuming you're using the image URI

                val request = Capsules(0, title, message, receiverEmail, "$date $time", null, null)
                RetrofitInstance.instance.createCapsule(request).enqueue(object : Callback<Capsules> {
                    override fun onResponse(call: Call<Capsules>, response: Response<Capsules>) {
                        if (response.isSuccessful && response.body() != null) {
                            val capsule = response.body()?.draft
                            Toast.makeText(this@CreateCapsule, capsule, Toast.LENGTH_SHORT).show()
                            displayName()
                        }
                    }

                    override fun onFailure(call: Call<Capsules>, t: Throwable) {
                        Toast.makeText(this@CreateCapsule, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(this, "Please fill in all fields!", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun displayName() {
        val intent = Intent()
        intent.putExtra("USERNAME", intent.getStringExtra("USERNAME")) // Pass the username back
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun sendBtn() {
        binding.sendBtn.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val message = binding.etMessage.text.toString()
            val date = binding.dateSchedule.text.toString()
            val schedule = binding.dateSchedule.text.toString()
//            val receiverEmail = binding.etReceiverEmail.text.toString()
        }
    }
}