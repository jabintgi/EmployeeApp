package com.infix.employeeapp.pages

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.infix.employeeapp.MainActivity
import com.infix.employeeapp.R
import com.infix.employeeapp.models.User
import com.infix.employeeapp.utils.AppPref
import com.infix.employeeapp.utils.AppPref.loggedUser
import com.infix.employeeapp.utils.Key
import com.infix.employeeapp.utils.Method
import kotlinx.android.synthetic.main.activity_login_page.*

class LoginPage : AppCompatActivity() {

    private val TAG = "LoginPageLogs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)
        animatePage()
        initView()
    }

    private fun initView() {
        btnCreateAccount.setOnClickListener { openRegisterPage() }
        btnLogin.setOnClickListener { submitForm() }
    }

    private fun openRegisterPage() {
        val intent = Intent(this, RegisterPage::class.java)
        startActivity(intent)
    }

    private fun submitForm() {
        val empId = etEmpId.text.toString().trim()
        val password = etPwd.text.toString().trim()

        ////valid
        if (empId.isNotEmpty() && password.isNotEmpty()) {

                pbLogin.visibility = View.VISIBLE
                btnLogin.visibility = View.GONE

                val userText = AppPref.getText(loggedUser)
                if (userText!!.isNotEmpty()) {
                    val user: User = Gson().fromJson(userText, User::class.java)
                    if (user.id == empId && user.password == password) {
                        AppPref.USER = user
                        openDashboard()
                        return
                    }
                }
                showError("You are an invalid user!")
        }
        ///form not valid
        else
            showError("Fill all fields")

    }

    private fun showError(msg: String) {
        pbLogin.visibility = View.GONE
        btnLogin.visibility = View.VISIBLE
        Method.showSnackBar(llLoginRoot, msg, Key.error)
    }

    private fun openDashboard() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun animatePage() {
        animation_view.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {
                //
            }

            override fun onAnimationEnd(p0: Animator?) {
                ////showing login form after animation complete
                llLogin.visibility = View.VISIBLE
            }

            override fun onAnimationCancel(p0: Animator?) {
                //
            }

            override fun onAnimationStart(p0: Animator?) {
                //
            }
        })
    }


}