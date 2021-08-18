package com.app.vendor.ui.auth

import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import com.app.vendor.R
import com.app.vendor.base.BaseActivity
import com.app.vendor.utils.AppUtil
import com.mobcoder.kitchen.base.BottomSheetType
import com.mobcoder.kitchen.viewModel.AuthViewModel
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : BaseActivity() {

    private val viewModel: AuthViewModel by viewModels()
    var MIN_PASSWORD_LENGTH = 6

    override fun onBottomSheetItemClicked(
        position: Int,
        type: BottomSheetType?,
        data: MediaStore.Images.Media?
    ) {
        TODO("Not yet implemented")
    }


    override fun layoutRes(): Int {
        return R.layout.activity_login

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        validatelogin()
    }




    private fun validatelogin() {
        btnLogin.setOnClickListener {
            if (etEmail.text.toString() == "") {
                Toast.makeText(this, "Email can't be blank", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (etEmail.text.toString().contains("@"))

            else{
                Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (etPassword.text.toString() == "") {
                Toast.makeText(this, "Password can't be blank", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (etPassword.text?.length!! < MIN_PASSWORD_LENGTH) {
                Toast.makeText(this, "Password should be more than 6 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

        }
    }







}
