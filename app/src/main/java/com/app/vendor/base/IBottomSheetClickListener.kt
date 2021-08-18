package com.app.vendor.base

import android.provider.MediaStore
import com.mobcoder.kitchen.base.BottomSheetType


interface IBottomSheetClickListener {
    fun onBottomSheetItemClicked(
        position: Int,
        type: BottomSheetType?,
        data: MediaStore.Images.Media?
    )
}