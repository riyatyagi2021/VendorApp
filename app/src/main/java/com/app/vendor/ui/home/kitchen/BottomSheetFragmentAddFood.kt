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
import com.app.vendor.callback.CallbackType
import com.app.vendor.callback.RootCallback
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.add_food_item.*
import kotlinx.android.synthetic.main.bottomsheet_addfood.*
import kotlinx.android.synthetic.main.bottomsheet_addfood.view.*
import kotlinx.android.synthetic.main.bottomsheet_imageupload_fragment.*


class BottomSheetFragmentAddFood(var call:editDelete): BottomSheetDialogFragment() {



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
        return inflater.inflate(R.layout.bottomsheet_addfood, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       tvEditFood.setOnClickListener {
           call.click(1)
        }
      tvDeleteFood.setOnClickListener {
          call.click(2)
        }
        view.ivCrossDelete.setOnClickListener { dismiss() }

    }
    private var rootCallback: RootCallback<Any>? = null
    private var data: Any? = null




    interface editDelete{
        fun click(v:Int)
    }


}


//view.ivCrossDelete.setOnClickListener { dismiss() }