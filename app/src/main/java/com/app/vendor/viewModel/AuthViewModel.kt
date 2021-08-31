package com.app.vendor.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.vendor.api.APICallHandler
import com.app.vendor.api.APICallManager
import com.app.vendor.api.APIType
import com.app.vendor.callback.CallbackType
import com.app.vendor.model.Media
import com.app.vendor.model.api.user.UserProfileResponse
import com.app.vendor.model.base.CommonApiResponse
import com.app.vendor.model.base.Errors
import com.app.vendor.model.food.FoodResponse
import com.app.vendor.model.food.ImageResponse
import com.app.vendor.model.food.VendorResponse
import com.app.vendor.model.user.FoodCreateRequest
import com.app.vendor.model.user.MyProfileResponse
import com.app.vendor.utils.AppConstant
import com.app.vendor.utils.AppUtil
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*


class AuthViewModel : ViewModel(), APICallHandler<Any> {


    var loginSuccess =
        MutableLiveData<UserProfileResponse>()

    var foodUserSuccess =
        MutableLiveData<FoodResponse>()

    var imageUploadResponse =
        MutableLiveData<ImageResponse>()

    var myProfileSuccess =
        MutableLiveData<MyProfileResponse>()

    var addFoodSuccess =
        MutableLiveData<CommonApiResponse>()

    var vendorListSuccess =
        MutableLiveData<VendorResponse>()

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

    fun addFoodImageUploadAPI(
        selectedMediaFiles: List<Media?>?
    ) {
        val multipartList: MutableList<MultipartBody.Part> =
            ArrayList()
        for (i in selectedMediaFiles!!.indices) {

                 val file = File(selectedMediaFiles[i]?.image)

            val requestFileSocialImage = RequestBody.create(
                AppUtil.getMimeType(selectedMediaFiles[i]?.image).toMediaTypeOrNull(), file)

            val socialImageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                AppConstant.MT.UPLOAD_MEDIA,
                file.name,
                requestFileSocialImage
            )

            multipartList.add(socialImageMultipart)
        }
        val apiCallManager =
            APICallManager(APIType.ADD_PHOTO, this)
        apiCallManager.addFoodImageUploadAPI(multipartList)
    }


    fun getMyProfile() {
        val apiCallManager = APICallManager(APIType.USER_PROFILE, this)
        apiCallManager.getMyProfile()
    }

    fun addFoodAPI(data: FoodCreateRequest?) {
        val apiCallManager = APICallManager(APIType.ADD_FOOD, this)
        apiCallManager.addFoodAPI(data)
    }


    fun getAllVendorAPI() {
        val apiCallManager = APICallManager(APIType.VENDOR_LIST, this)
        apiCallManager.getAllVendorAPI()
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

            APIType.ADD_PHOTO -> {
                val imageResponse = response as ImageResponse
                imageUploadResponse.setValue(imageResponse)
            }

            APIType.USER_PROFILE -> {
                val foodResponse = response as MyProfileResponse
                myProfileSuccess.setValue(foodResponse)
            }


            APIType.ADD_FOOD, APIType.UPDATE_FOOD -> {
                val foodResponse = response as CommonApiResponse
                addFoodSuccess.setValue(foodResponse)
            }

            APIType.VENDOR_LIST -> {
                val foodResponse = response as VendorResponse
                vendorListSuccess.setValue(foodResponse)
            }

            else -> {
            }
        }

    }

    override fun onFailure(apiType: APIType, error: Errors) {
        this.error.value = error
    }
}