package com.app.vendor.api

import com.app.vendor.model.api.user.UserProfileResponse
import com.app.vendor.model.base.BaseResponse
import com.app.vendor.model.base.CommonApiResponse
import com.app.vendor.model.food.FoodResponse
import com.app.vendor.model.food.ImageResponse
import com.app.vendor.model.food.VendorResponse
import com.app.vendor.model.user.FoodCreateRequest
import com.app.vendor.model.user.MyProfileResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*


interface IApiService {


    @FormUrlEncoded
    @POST("v1/employee/login")
    fun loginAPI(
        @Field("email") email: String?,
        @Field("password") password: String?
    ): Call<BaseResponse<UserProfileResponse?>>

    @GET("v1/food/list")
    fun getAllFoodAPI(
        @Query("vendorId") foodId: String?
    ): Call<BaseResponse<FoodResponse?>>

    @Multipart
    @POST("v1/upload/images")
    fun addPhotos(
        @Part mediaFiles: List<MultipartBody.Part>
    ): Call<BaseResponse<ImageResponse?>>


    @GET("v1/employee/getMyProfile")
    fun getMyProfile(): Call<BaseResponse<MyProfileResponse?>>

    @POST("v1/food/add")
    fun addFoodAPI(@Body paymentProduct: FoodCreateRequest?): Call<BaseResponse<CommonApiResponse?>>

    @DELETE("v1/food/delete")
    fun deleteFoodAPI(@Query ("foodId") foodId: String?):Call<BaseResponse<CommonApiResponse?>>

    @PUT("v1/food/update")
    fun updateFoodAPI(@Body paymentProduct: FoodCreateRequest?): Call<BaseResponse<CommonApiResponse?>>

    @GET("v1/food/vendors")
    fun getAllVendorAPI(): Call<BaseResponse<VendorResponse?>>

}
