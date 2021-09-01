package com.app.vendor.ui.home.kitchen

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.vendor.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.add_food_item.*
import kotlinx.android.synthetic.main.bottomsheet_imageupload_fragment.*


class BottomSheetFragmentCameraGallery(var callback:cameraGallery): BottomSheetDialogFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(
            STYLE_NORMAL,
            R.style.CustomBottomSheetDialogTheme
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottomsheet_imageupload_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txtCamera.setOnClickListener {
            callback.onclick(1)
        }
        txtGallery.setOnClickListener {
            callback.onclick(2)
        }
    }

    interface cameraGallery{
        fun onclick(v:Int)
    }

}