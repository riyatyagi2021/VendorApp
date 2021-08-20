package com.app.vendor.base

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.app.vendor.R
import com.app.vendor.ui.home.kitchen.KitchenDashboardActivity
import com.app.vendor.utils.AppUtil
import com.mobcoder.kitchen.base.BottomSheetType

import java.util.*


abstract class BaseActivity : AppCompatActivity(), IBottomSheetClickListener {

    private var progressDialog: ProgressDialog? = null

    abstract fun layoutRes(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes())
    }

    open fun launchActivity(classType: Class<out KitchenDashboardActivity>) {
        startActivity(Intent(this, classType))
    }


    open fun launchActivity(
        classType: Class<out BaseActivity>,
        bundle: Bundle,
        view: View
    ) {
        AppUtil.preventTwoClick(view)
        val intent = Intent(this, classType)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }

    open fun launchActivity(
        classType: Class<out BaseActivity>,
        bundle: Bundle
    ) {
        val intent = Intent(this, classType)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }

    open fun launchActivity(
        classType: Class<out BaseActivity>,
        view: View
    ) {
        AppUtil.preventTwoClick(view)
        startActivity(Intent(this, classType))
    }


    open fun launchActivityForResult(
        classType: Class<out BaseActivity>,
        requestCode: Int,
        view: View
    ) {
        AppUtil.preventTwoClick(view)
        val intent = Intent(this, classType)
        startActivityForResult(intent, requestCode)
    }

    open fun launchActivityForResult(
        classType: Class<out BaseActivity>,
        requestCode: Int,
        bundle: Bundle,
        view: View
    ) {
        AppUtil.preventTwoClick(view)
        val intent = Intent(this, classType)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivityForResult(intent, requestCode)
    }


    open fun launchActivityForResult(
        classType: Class<out BaseActivity>,
        bundle: Bundle,
        requestCode: Int
    ) {
        val intent = Intent(this, classType)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivityForResult(intent, requestCode)
    }

    open fun showProgressBar() {
        showProgressBar(true)
    }

    open fun showProgressBar(isCancel: Boolean) {
        hideProgressBar()
        if (!this@BaseActivity.isFinishing) {
            progressDialog = ProgressDialog.show(this@BaseActivity, "", "", true)
            if (progressDialog != null) {
                progressDialog?.setCanceledOnTouchOutside(isCancel)
                progressDialog?.setContentView(R.layout.progress_layout)
                Objects.requireNonNull(progressDialog?.getWindow())
                    ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
        }
    }

    /**
     * Hide progress bar
     */
    open fun hideProgressBar() {
        if (!this@BaseActivity.isFinishing) {
            if (progressDialog != null) {
                progressDialog?.dismiss()
                progressDialog = null
            }
        }
    }

    open override fun onBottomSheetItemClicked(
        position: Int,
        type: BottomSheetType?,
        data: MediaStore.Images.Media?
    ) {

    }

    open fun hideSoftKeyBoard() {
        val imm =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (currentFocus != null) {
            imm.hideSoftInputFromWindow(
                currentFocus?.windowToken,
                InputMethodManager.RESULT_UNCHANGED_SHOWN
            )
        }
    }

    open fun onStoragePickUp(data: MutableList<MediaStore.Images.Media>?) {}
}