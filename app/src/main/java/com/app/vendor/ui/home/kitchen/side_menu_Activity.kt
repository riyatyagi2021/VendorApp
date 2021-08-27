package com.app.vendor.ui.home.kitchen

import android.os.Bundle
import androidx.activity.viewModels
import com.app.vendor.R
import com.app.vendor.base.BaseActivity
import com.app.vendor.utils.AppUtil
import com.mobcoder.kitchen.viewModel.AuthViewModel

class side_menu_Activity: BaseActivity() {

    private val viewModel: AuthViewModel by viewModels()


    override fun layoutRes(): Int {
        return (R.layout.side_menu)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setObservables()


    }

    private fun setObservables() {
        viewModel.myProfileSuccess.observe(this) { data ->
           /* val w = data.profileData?.walletBalance
            val c = data.profileData?.couponWalletBalance

            tvWM.text = w?.let { AppUtil.getRupee(w) }
            tvCWM.text = c?.let { AppUtil.getRupee(c) }*/
        }

        viewModel.error.observe(this) { errors ->
            hideProgressBar()
            //swp.isRefreshing = false
            AppUtil.showToast(errors.errorMessage)
        }
    }


}