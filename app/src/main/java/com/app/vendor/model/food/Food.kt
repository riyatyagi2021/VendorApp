package com.app.vendor.model.food

import java.io.Serializable

class Food :Serializable{


    var name: String? = null
    var foodId: String? = null
    var price: Float = 0.0f
    var status: Int? = null
    var description: String? = null
    var isAvailable: Int? = null
    var availableQuantity: Int? = null
    var images: List<String>? = null
  //  var vendorDetail: VendorDetail? = null
    var isDeleted: Int? = null

}