package com.app.vendor.api

import com.app.vendor.model.base.BaseResponse
import com.app.vendor.model.food.FoodResponse
import com.mobcoder.kitchen.model.api.user.UserProfileResponse
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



}
