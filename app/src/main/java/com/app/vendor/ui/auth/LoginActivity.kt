package com.app.vendor.ui.auth

import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatEditText
import com.app.vendor.R
import com.app.vendor.base.BaseActivity
import com.app.vendor.ui.home.kitchen.KitchenDashboardActivity
import com.app.vendor.utils.AppUtil
import com.app.vendor.utils.PreferenceKeeper
import com.mobcoder.kitchen.base.BottomSheetType
import com.mobcoder.kitchen.viewModel.AuthViewModel
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : BaseActivity() {

    private val viewModel: AuthViewModel by viewModels()

    var MIN_PASSWORD_LENGTH = 6

    override fun layoutRes(): Int {
        return R.layout.activity_login
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setListener()
        setObservables()
    }

    private fun setObservables() {
        viewModel.loginSuccess.observe(this) { data ->

            AppUtil.showToast(data?.message)
            hideProgressBar()

            PreferenceKeeper.getInstance().isLogin = true
            val useData = data?.employeeProfile
            PreferenceKeeper.getInstance().accessToken = useData?.accessToken
            PreferenceKeeper.getInstance().setUser(useData)
            launchActivity(KitchenDashboardActivity::class.java)
           finish()
        }

        viewModel.error.observe(this) { errors ->
            hideProgressBar()
            AppUtil.showToast(errors.errorMessage)
        }
    }

    private fun setListener() {
        btnLogin.setOnClickListener {
            loginAPI()
            showProgressBar()
            hideSoftKeyBoard()
        }

    }
        fun loginAPI() {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
           if(loginEmailAPI(email, password))
               showProgressBar()
            viewModel.loginAPI(email, password)
        }

        fun loginEmailAPI(email: String, password: String): Boolean {
            if (TextUtils.isEmpty(email)) {
                AppUtil.showToast("Email cannot be blank.")
                return false
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                AppUtil.showToast("Invalid Email")
                return false
            }
            if (TextUtils.isEmpty(password)) {
                AppUtil.showToast("Password cannot be blank.")
                return false
            }

            if (password.length!! < MIN_PASSWORD_LENGTH) {
                AppUtil.showToast("Password should be more than 6 characters")
                return false
            }
             else{
                AppUtil.showToast("Login Successful")
            }
            return true
        }
    }





