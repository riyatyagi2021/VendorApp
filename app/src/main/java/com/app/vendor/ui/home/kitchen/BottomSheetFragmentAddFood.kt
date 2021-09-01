package com.app.vendor.ui.home.kitchen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.vendor.R
import com.app.vendor.callback.CallbackType
import com.app.vendor.callback.RootCallback
import com.app.vendor.model.food.Food
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottomsheet_addfood.*
import kotlinx.android.synthetic.main.bottomsheet_addfood.view.*


class BottomSheetFragmentAddFood(var foodData: Food, var callback:RootCallback<Any>): BottomSheetDialogFragment() {

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
           callback.onRootCallback(1, foodData, CallbackType.UPDATE_FOOD,it)
//           call.click(foodId,1)
           dismiss()
        }
      tvDeleteFood.setOnClickListener {
          callback.onRootCallback(2, foodData, CallbackType.DELETE_FOOD,it)
//          call.click(foodId,2)
          dismiss()
        }
        view.ivCrossDelete.setOnClickListener { dismiss() }
    }
   /* private var rootCallback: RootCallback<Any>? = null
    private var data: Any? = null*/
}

