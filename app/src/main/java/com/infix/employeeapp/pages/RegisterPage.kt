package com.infix.employeeapp.pages

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.infix.employeeapp.R
import com.infix.employeeapp.utils.Key
import com.infix.employeeapp.utils.Method
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_register_page.*
import java.util.*


class RegisterPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)

        initView()

    }

    private fun initView() {
        cvRegisterDp.setOnClickListener { setProfile() }
        btnGoBack.setOnClickListener { finish() }
        btnRegister.setOnClickListener { submitForm() }
    }

    private fun submitForm() {
        val randomId = (UUID.randomUUID()).toString() //Eg: 147db7ef-57db-4b65-a87a-1eafd183d908
        val empID = "#emp ${randomId.substring(9, 13)}"  // #emp57db
    }

    private fun setProfile() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    (arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)),
                    1
                )
            } else imagePicker()
        } else imagePicker()
    }

    private fun imagePicker() {
        CropImage.activity()
            .setGuidelines(CropImageView.Guidelines.ON)
            .setAspectRatio(1, 1)
            .setAutoZoomEnabled(true)
            .start(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                val resultUri: Uri = result.uri
                cvRegisterDp.setImageURI(resultUri)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Method.showSnackBar(clRootRegister, "Error on choosing profile image!", Key.error)
            }
        }
    }

}