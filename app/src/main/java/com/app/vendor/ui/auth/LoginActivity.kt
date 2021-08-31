package com.app.vendor.ui.auth

import android.os.Bundle
import android.text.TextUtils
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import androidx.activity.viewModels
import com.app.vendor.R
import com.app.vendor.base.BaseActivity
import com.app.vendor.ui.home.kitchen.KitchenDashboardActivity
import com.app.vendor.utils.AppUtil
import com.app.vendor.utils.PreferenceKeeper
import com.app.vendor.viewModel.AuthViewModel
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

        }

        viewModel.error.observe(this) { errors ->
            hideProgressBar()
            AppUtil.showToast(errors.errorMessage)
        }
    }

    private fun setListener() {

        showPassword()
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

            return true
        }



    private fun showPassword() {
        ivPasswordHide?.setOnClickListener {
            val t = it.tag.toString().toInt()
            if (t == 0) {
                ivPasswordHide.setImageResource(R.drawable.ic_password_show)
                etPassword.transformationMethod = null
                ivPasswordHide.tag = "1"
            } else {
                ivPasswordHide.setImageResource(R.drawable.ic_password_hide)
                etPassword.transformationMethod = PasswordTransformationMethod()
                ivPasswordHide.tag = "0"
            }
        }
    }


    }





