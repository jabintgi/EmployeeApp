package com.infix.employeeapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.infix.employeeapp.models.User
import com.infix.employeeapp.pages.LoginPage
import com.infix.employeeapp.utils.AppPref
import com.infix.employeeapp.utils.AppPref.loggedUser
import com.infix.employeeapp.utils.Method
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var user:User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppPref.getInstance(this)

        val userText = AppPref.getText(loggedUser)
        if (userText!!.isEmpty()) {
            openLogin()
            return
        }

        Method.showLog(userText)

        user = Gson().fromJson(userText, User::class.java)
        setContentView(R.layout.activity_main)
        initView()

        Method.showLog(user.profileImg)
        initData()
    }

    private fun initView() {
        btnLogout.setOnClickListener {
            AppPref.putText(loggedUser,"")
            openLogin()
        }
    }

    private fun openLogin() {
        val intent = Intent(this, LoginPage::class.java)
        startActivity(intent)
        finish()
    }

    private fun initData() {

        Glide.with(this).load(user.profileImg).placeholder(R.drawable.user).into(profile_image)

        tvEmpID.text = user.id
        tvName.text = user.name
        tvMob.text = user.mobile
        tvBlood.text = user.bloodGroup
        tvQualification.text = user.qualification
        tvAddress.text = user.address
        tvLandLine.text = user.landLineNo
        tvEmail.text = user.email
    }
}