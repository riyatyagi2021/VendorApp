package com.app.vendor.utils

import android.app.AlertDialog
import android.content.DialogInterface
import com.app.vendor.R
import com.app.vendor.base.BaseActivity

object DialogUtils {

    fun showAlertAppSetting(
        activity: BaseActivity,
        permissionMsg: String
    ) {
        val dialog = AlertDialog.Builder(activity)
            .setTitle(activity.getString(R.string.app_name))
            .setMessage(permissionMsg)
            .setPositiveButton(activity.getString(R.string.yes)) { dialog1: DialogInterface?, which: Int ->

            }
            .setNegativeButton(activity.getString(R.string.no)) { dialog12: DialogInterface?, which: Int ->
                dialog12?.dismiss()
            }.show()
        dialog.setCanceledOnTouchOutside(false)
       /* val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
        val negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
        positiveButton.setTextColor(getColors("colorBlack"))
        negativeButton.setTextColor(AppUtil.getColors(R.color.colorBlack))*/
    }

}