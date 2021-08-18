package com.app.vendor.utils

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.view.View
import android.widget.Toast
import com.app.vendor.base.App
import com.app.vendor.base.App.Companion.INSTANCE
import java.util.*


object AppUtil {

    fun preventTwoClick(view: View) {
        view.setEnabled(false)
        view.postDelayed(Runnable { view.setEnabled(true) }, 800)
    }

    fun showToast(message: String?) {
        Toast.makeText(INSTANCE, message, Toast.LENGTH_SHORT).show()
    }




    fun getDeviceOS(): String {
        var codeName = "UNKNOWN"
        try {
            val fields = Build.VERSION_CODES::class.java.fields

            fields.filter { it.getInt(Build.VERSION_CODES::class) == Build.VERSION.SDK_INT }
                .forEach { codeName = it.name }
        } catch (e: Exception) {

        }
        return codeName
    }


}