package com.example.retrofittest

import android.app.Activity
import android.content.Intent
import android.location.Location
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {




    private var pictureIV : ImageView? = null

    private lateinit var photoFile: File
    lateinit var currentPhotoPath: String
    private val PICTURE_FROM_CAMERA: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        registerListener()
    }

    private fun initViews(){
        pictureIV = findViewById(R.id.picture)
    }

    private fun registerListener(){
        pictureIV!!.setOnClickListener {
            takePicture()
        }

    }

    private fun takePicture(){
        val pictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        photoFile = createImageFile()
        val uri = FileProvider.getUriForFile(this, "com.example.retrofittest.fileprovider", photoFile)
        pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        startActivityForResult(pictureIntent, PICTURE_FROM_CAMERA)
    }

    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
           // galleryAddPic()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {

            if ( requestCode ==  PICTURE_FROM_CAMERA  ){
                val uri = FileProvider.getUriForFile(this, "com.example.retrofittest.fileprovider", photoFile)
                pictureIV!!.setImageURI(uri)
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }

    }


}