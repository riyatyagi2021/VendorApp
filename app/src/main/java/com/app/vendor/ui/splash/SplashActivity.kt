package com.app.vendor.ui.splash

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import com.app.vendor.BuildConfig
import com.app.vendor.R
import com.app.vendor.base.BaseActivity
import com.app.vendor.ui.auth.LoginActivity
import com.app.vendor.ui.home.kitchen.KitchenDashboardActivity
import com.app.vendor.utils.AppConstant
import com.app.vendor.utils.PreferenceKeeper
import com.mobcoder.kitchen.base.BottomSheetType

import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashActivity : BaseActivity() {


    override fun layoutRes(): Int {
        return R.layout.activity_splash
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (BuildConfig.FLAVOR.contains("prod")) {
            tvVersion?.text = "prod: ${BuildConfig.VERSION_NAME}"
        } else {
            tvVersion?.text = "dev: ${BuildConfig.VERSION_NAME}"
        }
        navigateActivity()
    }

    override fun onBottomSheetItemClicked(
        position: Int,
        type: BottomSheetType?,
        data: MediaStore.Images.Media?
    ) {
        TODO("Not yet implemented")
    }

    private fun navigateActivity() {
        GlobalScope.launch { // context of the parent, main runBlocking coroutine
            delay(AppConstant.SPLASH_DELAY)

            gotoScreen()

        }
    }

    private fun gotoScreen() {
        val isLogin = PreferenceKeeper.getInstance().isLogin
        if (isLogin) {
            startActivity(Intent(this, KitchenDashboardActivity::class.java))
        } else {
            startActivity(Intent(this, LoginActivity::class.java))

            finish()
        }


    }
}