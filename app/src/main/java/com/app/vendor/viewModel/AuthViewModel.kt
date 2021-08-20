package com.mobcoder.kitchen.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.vendor.api.APICallHandler
import com.app.vendor.api.APICallManager
import com.app.vendor.api.APIType
import com.app.vendor.model.base.Errors
import com.app.vendor.model.food.FoodResponse
import com.mobcoder.kitchen.model.api.user.UserProfileResponse
import java.util.*


class AuthViewModel : ViewModel(), APICallHandler<Any> {


    var loginSuccess =
        MutableLiveData<UserProfileResponse>()

    var foodUserSuccess =
        MutableLiveData<FoodResponse>()

    var error =
        MutableLiveData<Errors>()


    fun loginAPI(
        email: String?,
        password: String?
    ) {
        val apiCallManager = APICallManager(APIType.SIGN_IN, this)
        apiCallManager.loginAPI(email, password)
    }

    fun getFoodUser(vendorId : String?) {
        val apiCallManager = APICallManager(APIType.FOOD_LIST_USER, this)
        apiCallManager.getAllFoodAPI(vendorId)
    }


    override fun onSuccess(apiType: APIType, response: Any?) {

        when (apiType) {

            APIType.SIGN_IN -> {
                val userProfileResponse = response as UserProfileResponse
                loginSuccess.setValue(userProfileResponse)
            }
            APIType.FOOD_LIST_USER -> {
                val foodResponse = response as FoodResponse
                foodUserSuccess.setValue(foodResponse)
            }


            else -> {
            }
        }

    }

    override fun onFailure(apiType: APIType, error: Errors) {
        this.error.value = error
    }
}