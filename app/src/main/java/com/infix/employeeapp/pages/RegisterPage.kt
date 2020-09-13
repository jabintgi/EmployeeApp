package com.infix.employeeapp.pages

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.infix.employeeapp.MainActivity
import com.infix.employeeapp.R
import com.infix.employeeapp.models.User
import com.infix.employeeapp.utils.AppPref
import com.infix.employeeapp.utils.AppPref.loggedUser
import com.infix.employeeapp.utils.Key
import com.infix.employeeapp.utils.Method
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_register_page.*
import java.util.*


class RegisterPage : AppCompatActivity() {

     var profileImage: Uri ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)
        initView()
    }

    private fun initView() {
        cvRegisterDp.setOnClickListener { setProfile() }
        btnGoBack.setOnClickListener { finish() }
        btnRegister.setOnClickListener { submitForm() }
        etDOJ.setOnClickListener { showDatePicker() }
    }

    private fun submitForm() {

        val name = etName.text.toString().trim()
        val mob = etMob.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val address = etAddress.text.toString().trim()
        val qualification = etQualification.text.toString().trim()
        val bloodGroup = etBlood.text.toString().trim()
        val landlineNo = etLand.text.toString().trim()

        val companyName = etCompany.text.toString().trim()
        val branch = etBranch.text.toString().trim()
        val department = etDept.text.toString().trim()
        val designation = etDesignation.text.toString().trim()
        val DOJ = etDOJ.text.toString().trim()
        val jobType = etJobType.text.toString().trim()
        val reportTo = etReportTo.text.toString().trim()
        val pwd = etPwd.text.toString().trim()
        val cpwd = etCpwd.text.toString().trim()
        val nrc = etNRC.text.toString().trim()

        if(name.isEmpty() || mob.isEmpty() || email.isEmpty() || address.isEmpty() || qualification.isEmpty() || bloodGroup.isEmpty()
            || landlineNo.isEmpty() || companyName.isEmpty() || branch.isEmpty() || department.isEmpty() || designation.isEmpty()
            || DOJ.isEmpty() || jobType.isEmpty() || reportTo.isEmpty() || pwd.isEmpty() || cpwd.isEmpty() || nrc.isEmpty())
        {
            showError("Fill all fields")
            return
        }else if (pwd != cpwd){
            showError("Password not matched!")
            etCpwd.requestFocus()
            return
        }
        else if(profileImage==null){
            showError("Set a profile image also")
            return
        }

        val randomId = (UUID.randomUUID()).toString() //Eg: 147db7ef-57db-4b65-a87a-1eafd183d908
        val empID = "#emp${randomId.substring(9, 13)}"  // #emp57db



        Method.showLog("$profileImage")

        val newUser = User(empID,name,branch,address,department,designation,DOJ, ""+profileImage,mob,jobType,email,
        landlineNo,bloodGroup,companyName,nrc,qualification,reportTo,pwd)

        AppPref.putText(loggedUser, Gson().toJson(newUser))
        openDashboard()
    }

    private fun openDashboard() {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }

    private fun showError(msg: String) {
        Method.showSnackBar(clRootRegister,msg,Key.error)
    }

    private fun showDatePicker() {
        val calender = Calendar.getInstance()
        val picker = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener { _, year, month, day ->
                calender.set(year, month, day)
                etDOJ.setText(
                    android.text.format.DateFormat.format("dd MMM yyyy", calender).toString()
                )
            },
            calender.get(Calendar.YEAR),
            calender.get(Calendar.MONTH),
            calender.get(Calendar.DAY_OF_MONTH)
        )
        picker.show()
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
                profileImage = result.uri

                Method.showLog("original:${result.originalUri}")
                Method.showLog("uri:${result.uri}")

                cvRegisterDp.setImageURI( result.uri)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Method.showSnackBar(clRootRegister, "Error on choosing profile image!", Key.error)
            }
        }
    }

}