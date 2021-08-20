package com.app.vendor.api

import com.app.vendor.model.base.BaseResponse
import com.mobcoder.kitchen.model.api.user.UserProfileResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface IApiService {


    @FormUrlEncoded
    @POST("v1/employee/login")
    fun loginAPI(
        @Field("email") email: String?,
        @Field("password") password: String?
    ): Call<BaseResponse<UserProfileResponse?>>




}
