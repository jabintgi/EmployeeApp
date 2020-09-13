package com.infix.employeeapp.models

import android.graphics.Bitmap
import android.net.Uri

data class User (
    val id:String,
    val name:String,
    val branch:String,
    val address:String,
    val department:String,
    val designation:String,
    val DOJ:String,
    val profileImg: String,
    val mobile:String,
    val jobType:String,
    val email:String,
    val landLineNo:String,
    val bloodGroup:String,
    val companyName:String,
    val nrcNumber:String,
    val qualification:String,
    val reportingTo:String,
    val password:String
)