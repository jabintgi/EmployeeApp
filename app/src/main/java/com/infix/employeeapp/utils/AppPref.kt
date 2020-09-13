package com.infix.employeeapp.utils

import android.content.Context
import android.content.SharedPreferences
import com.infix.employeeapp.models.User

object AppPref {

    private var pref: SharedPreferences? = null

    const val loggedUser = "loggedUser"
    var isLogged = ""

    @Synchronized
    fun getInstance(c: Context) {
        pref = c.getSharedPreferences(c.packageName + "employeeAppPrefs", Context.MODE_PRIVATE)
    }

    @Synchronized
    fun putText(key: String?, data: String?) {
        pref!!.edit().putString(key, data).apply()
    }

    @Synchronized
    fun getText(key: String): String? {
        return pref!!.getString(key, "")
    }

}