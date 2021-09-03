package com.app.vendor.ui.home.kitchen

import android.os.Bundle
import androidx.activity.viewModels
import com.app.vendor.R
import com.app.vendor.base.BaseActivity
import com.app.vendor.model.food.Food
import com.app.vendor.model.user.FoodCreateRequest
import com.app.vendor.utils.AppConstant
import com.app.vendor.utils.AppUtil
import com.app.vendor.viewModel.AuthViewModel
import kotlinx.android.synthetic.main.edit_food_item.*

class EditFoodActivity : BaseActivity() {

    private var foodId: String? = null
    private val viewModel: AuthViewModel by viewModels()

    override fun layoutRes(): Int {
        return R.layout.edit_food_item
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getBundleData()
        initListeners()
        setObservables()
    }

    private fun initListeners() {
        ivBack.setOnClickListener {
            AppUtil.preventTwoClick(it)
            AppUtil.showToast("CLick")
            onBackPressed()
        }

        btnSaveFood.setOnClickListener {
            AppUtil.preventTwoClick(it)

            val foodName = editFoodName.text.toString().trim()
            val foodPrice = editFoodPrice.text.toString().trim()
            val foodQty = editFoodAQ.text.toString().trim()

            if (foodName.isNotEmpty() && foodPrice.isNotEmpty() && foodQty.isNotEmpty()) {
                updateFoodAPI(foodName, foodPrice, foodQty)
            } else {
            AppUtil.showToast("Fill every details")
            }
        }
    }

    private fun updateFoodAPI(foodName: String, foodPrice: String, foodQty: String) {
        if (AppUtil.isConnection()) {
            val foodReq = FoodCreateRequest()
            foodReq.foodId = foodId
            foodReq.isAvailable = 1
            foodReq.status = 1
            foodReq.name = foodName
            foodReq.price = foodPrice.toFloat()
            foodReq.availableQuantity = foodQty.toInt()
            println("MMMMMMMMMM: FOOD DATA: ${foodReq.foodId}")
            viewModel.updateFoodAPI(foodReq)
        } else {
            AppUtil.showToast(getString(R.string.msg_network_connection))
        }
    }

    private fun getBundleData() {
        val bundle: Bundle? = intent.extras
        bundle?.let {
            val foodData = it.getSerializable(AppConstant.BK.FOOD_DATA) as Food?
            foodId = foodData?.foodId

            println("MMMMMMMMMM: FOOD ID: $foodId")
            setDataOnUI(foodData)
        }
    }

    private fun setDataOnUI(foodData: Food?) {
        foodData?.let {
            editFoodName.setText(it.name)
            editFoodPrice.setText(it.price.toString())
            editFoodAQ.setText(it.availableQuantity.toString())
        }
    }

    private fun setObservables() {
        viewModel.addFoodSuccess.observe(this) { data ->
            hideProgressBar()
            AppUtil.showToast(data?.message)
            finish()
        }


        viewModel.error.observe(this) { errors ->
            hideProgressBar()
            AppUtil.showToast(errors.errorMessage)
        }
    }
}