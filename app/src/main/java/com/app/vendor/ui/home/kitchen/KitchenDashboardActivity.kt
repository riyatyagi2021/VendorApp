package com.app.vendor.ui.home.kitchen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.viewModels
import com.app.vendor.R
import com.app.vendor.base.BaseActivity
import com.app.vendor.model.food.Food
import com.app.vendor.utils.AppUtil
import com.mobcoder.kitchen.base.BottomSheetType
import com.mobcoder.kitchen.callback.RootCallback
import com.mobcoder.kitchen.viewModel.AuthViewModel
import kotlinx.android.synthetic.main.activity_dashboard.*

class KitchenDashboardActivity:BaseActivity() {

    private var foodAdapter: FoodAdapter? = null
    private val viewModel: AuthViewModel by viewModels()
    var vendorId: String? = null
    private var foods: MutableList<Food>? = null


    override fun layoutRes(): Int {
        return R.layout.activity_dashboard
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAdapter()
        setObservables()

        if (AppUtil.isConnection()) {
            showProgressBar()
            viewModel.getFoodUser(vendorId)
        }

    }

    private fun setAdapter() {
        foodAdapter = FoodAdapter()
        rv1.setHasFixedSize(true) //every item of the RecyclerView has a fix size
        rv1.adapter = foodAdapter
       foodAdapter?.setRootCallback(this as RootCallback<Any>)
    }

    private fun setObservables() {

        viewModel.foodUserSuccess.observe(this) { data ->
           // hideProgressBar()
            //  swpKt.isRefreshing = false
            foods = data.foodItemList

            /*  if (foods == null || foods?.size == 0) {
                tvNoDataKt.visible()
                tvNoDataKt.text = "No food available for users"
            } else {
                tvNoDataKt.gone()
            }
            tvItemCountKt.gone()
            linearCartKt.gone()
            foodAdapter?.setData(foods)
        }*/

             viewModel.error.observe(this) { errors ->
           // swpKt.isRefreshing = false
            hideProgressBar()
            AppUtil.showToast(errors.errorMessage)
        }
        }


    }


 /*   override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppConstant.REQUEST_CODES.REQ_BUY_NOW_CODE) {
            val b = data?.extras
            val index = b?.getInt(AppConstant.BK.PAYMENT)
            if (resultCode == Activity.RESULT_OK) {
                if (index == 1 || index == 2) { // 1 for payment done, 2 for cancel, null back pressed
                    if (AppUtil.isConnection()) {
                        showProgressBar()
                        viewModel.getFoodUser(vendorId)
                    }
                }
            }
        }
    }*/
}