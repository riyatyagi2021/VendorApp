package com.app.vendor.api

import android.content.Intent
import com.app.vendor.base.App
import com.app.vendor.model.api.user.UserProfileResponse
import com.app.vendor.model.base.BaseResponse
import com.app.vendor.model.base.CommonApiResponse
import com.app.vendor.model.base.Errors
import com.app.vendor.model.food.FoodResponse
import com.app.vendor.model.food.ImageResponse
import com.app.vendor.model.food.VendorResponse
import com.app.vendor.model.user.FoodCreateRequest
import com.app.vendor.model.user.MyProfileResponse
import com.app.vendor.ui.auth.LoginActivity
import com.app.vendor.utils.PreferenceKeeper
import okhttp3.MultipartBody
import retrofit2.Callback
import retrofit2.Response
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


class APICallManager<T>(
    private val mCallType:APIType,
    private val mAPICallHandler: APICallHandler<T>
) : Callback<BaseResponse<T>> {


    override fun onResponse(call: retrofit2.Call<BaseResponse<T>>?, response: Response<BaseResponse<T>>) {
        if (response.code() == APIStatusCode.OK || response.code() == APIStatusCode.CREATED || response.code() == APIStatusCode.NO_CONTENT) {
            val body = response.body()
            if (body != null) {
                if (body.statusCode == 1) {
                    mAPICallHandler.onSuccess(mCallType, body.responseData)
                } else {
                    if (body.error != null) {

                        if (body.error?.errorCode == 17) {
                            PreferenceKeeper.getInstance().isLogin = false
                            PreferenceKeeper.getInstance().accessToken = null
                            val intent =
                                Intent(App.INSTANCE, LoginActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            App.INSTANCE.startActivity(intent)

                        } else {
                            mAPICallHandler.onFailure(mCallType, body.error!!)
                        }
                    } else {
                        val errors = Errors()
                        val errorMessage =
                        "something went wrong"
                        errors.errorMessage = errorMessage
                        mAPICallHandler.onFailure(mCallType, errors)
                    }
                }
            }
        } else {
            val errors = Errors()
            val errorMessage =
               "something went wrong"
            errors.errorMessage = errorMessage
            mAPICallHandler.onFailure(mCallType, errors)
        }
    }

    override fun onFailure(call: retrofit2.Call<BaseResponse<T>>?, throwable: Throwable) {
        val errorCode = 0
        val message: String? =
            if (throwable is UnknownHostException || throwable is SocketException || throwable is SocketTimeoutException) {
               "something went wrong"
            } else {
                throwable.message
            }
        val errors = Errors()
        errors.errorMessage = message
        mAPICallHandler.onFailure(mCallType, errors)
    }

    fun loginAPI(
        email: String?,
        password: String?
    ) {
        APIClient.getClient()
            .loginAPI(email, password)
            .enqueue(this@APICallManager as retrofit2.Callback<BaseResponse<UserProfileResponse?>>)
    }


    fun getAllFoodAPI(vendorId : String?) {
        APIClient.getClient().getAllFoodAPI(vendorId)
            .enqueue(this@APICallManager as Callback<BaseResponse<FoodResponse?>>)
    }


    fun addFoodImageUploadAPI(
        multipartList: List<MultipartBody.Part>
    ) {
        APIClient.getClient()
            .addPhotos(multipartList)
            .enqueue(this@APICallManager as Callback<BaseResponse<ImageResponse?>>)
    }


    fun getMyProfile(
    ) {
        APIClient.getClient()
            .getMyProfile()
            .enqueue(this@APICallManager as Callback<BaseResponse<MyProfileResponse?>>)
    }


    fun addFoodAPI(
        data: FoodCreateRequest?
    ) {
        APIClient.getClient()
            .addFoodAPI(data)
            .enqueue(this@APICallManager as Callback<BaseResponse<CommonApiResponse?>>)
    }


    fun deleteFoodAPI(foodId:String?){
        APIClient.getClient()
            .deleteFoodAPI(foodId)
            .enqueue( this@APICallManager as Callback<BaseResponse<CommonApiResponse?>>)
    }

    fun updateFoodAPI(  data: FoodCreateRequest?){
        APIClient.getClient()
            .updateFoodAPI(data)
            .enqueue(this@APICallManager as Callback<BaseResponse<CommonApiResponse?>>)
    }

    fun getAllVendorAPI() {
        APIClient.getClient()
            .getAllVendorAPI()
            .enqueue(this@APICallManager as Callback<BaseResponse<VendorResponse?>>)
    }

}
