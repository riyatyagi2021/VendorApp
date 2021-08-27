package com.app.vendor.ui.home.kitchen

import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.viewModels
import com.app.vendor.base.BaseActivity
import com.app.vendor.model.Media
import com.app.vendor.utils.AppUtil
import com.google.android.play.core.internal.aq
import com.mobcoder.kitchen.viewModel.AuthViewModel
import kotlinx.android.synthetic.main.add_food_item.*
import kotlin.collections.ArrayList

class AddFoodActivity:BaseActivity() {

    override fun layoutRes(): Int {
        TODO("Not yet implemented")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        btnAddFood.setOnClickListener({

            if(AppUtil.isConnection()){
                val name=etFoodName.text.toString()
                val price=etFoodPrice.text.toString()
                val aq=etFoodAQ.text.toString()

                /* if(addFoodValidate(name,price, aq)){
           showProgressBar()
       }*/
            }
        })
        /* private fun addFoodValidate(name: String, price: Int, aq: Int): Boolean {

           }*/

    }
}