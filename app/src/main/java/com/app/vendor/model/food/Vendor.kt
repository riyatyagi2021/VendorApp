package com.app.vendor.model.food

import java.io.Serializable

class Vendor : Serializable {
    var vendorId: String? = null
    var name: String? = null
    var phoneNo: String? = null
    var email: String? = null
    var image: String? = null
    var aboutUs: String? = null
    var isSelected: Boolean = false
}