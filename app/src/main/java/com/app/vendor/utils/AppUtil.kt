package com.app.vendor.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.text.TextUtils
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Toast
import com.app.vendor.R
import com.app.vendor.base.App
import com.app.vendor.base.App.Companion.INSTANCE
import java.io.File
import java.util.*


object AppUtil {

    fun preventTwoClick(view: View) {
        view.setEnabled(false)
        view.postDelayed(Runnable { view.setEnabled(true) }, 800)
    }

    fun showToast(message: String?) {
        Toast.makeText(INSTANCE, message, Toast.LENGTH_SHORT).show()
    }

    fun isConnection(): Boolean {
        return isConnection(true)
    }

    fun isConnection(notShowMsg: Boolean): Boolean {
        val connectivityManager = App.INSTANCE
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo =
            Objects.requireNonNull(connectivityManager)
                .activeNetworkInfo
        val isNetwork =
            activeNetworkInfo != null && activeNetworkInfo.isConnected
        if (!isNetwork && !notShowMsg) {
            showToast(
                App.INSTANCE.getResources()
                    .getString(R.string.msg_network_connection)
            )
        }
        return isNetwork
    }



    fun getMimeType(url: String?): String {
        val path = Uri.fromFile(File(url))
        val type_map = MimeTypeMap.getSingleton()
        var extension = MimeTypeMap.getFileExtensionFromUrl(path.toString())
        extension = extension.toLowerCase(Locale.ROOT)
        if (extension.contains(".")) {
            extension = extension.substring(extension.lastIndexOf("."))
        }
        return type_map.getMimeTypeFromExtension(extension).toString()
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

    fun getRupee(price: Float): String {
        return "\u20B9${price}"
    }


    fun getFullName(firstName: String?, lastName: String?): String {
        var name1 = "NA"
        var name2: String? = ""
        if (!TextUtils.isEmpty(firstName)) {
            name1 = "$firstName "
        }
        if (!TextUtils.isEmpty(lastName)) {
            name2 = lastName
        }
        return name1 + "" + name2
    }


}