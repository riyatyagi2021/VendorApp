package com.app.vendor.ui.home.kitchen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.vendor.R
import com.app.vendor.base.BaseActivity

class FoodActivity :BaseActivity(){
    override fun layoutRes(): Int {
     return R.layout.add_food_item
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}