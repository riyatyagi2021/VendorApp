package com.app.vendor.api

import com.app.vendor.model.base.Errors


interface APICallHandler<T> {

   fun onSuccess(apiType: APIType, response: T?)

    fun onFailure(apiType: APIType, error: Errors)
}
