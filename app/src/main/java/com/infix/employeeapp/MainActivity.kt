package com.infix.employeeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import com.infix.employeeapp.models.User
import com.infix.employeeapp.pages.LoginPage
import com.infix.employeeapp.utils.AppPref
import com.infix.employeeapp.utils.AppPref.loggedUser

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppPref.getInstance(this)


        val userText = AppPref.getText(loggedUser)
        if (userText!!.isEmpty()) {
            val intent  = Intent(this,LoginPage::class.java)
            startActivity(intent)
            finish()
            return
        }

        setContentView(R.layout.activity_main)
        AppPref.USER = Gson().fromJson(userText, User::class.java)
        initData()
    }

    private fun initData() {

    }
}