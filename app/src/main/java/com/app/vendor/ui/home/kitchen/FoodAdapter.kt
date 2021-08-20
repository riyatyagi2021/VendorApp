package com.app.vendor.ui.home.kitchen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.vendor.R
import com.app.vendor.model.food.Food
import com.app.vendor.utils.AppUtil
import com.app.vendor.utils.GlideUtils
import com.mobcoder.kitchen.callback.RootCallback
import kotlinx.android.synthetic.main.adapter_kitchen.view.*

class FoodAdapter() :
    RecyclerView.Adapter<FoodAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_kitchen, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = products?.get(position)
        holder.bind(data, position)

    }

    override fun getItemCount(): Int {
        return products?.size ?: 0
    }

    private var rootCallback: RootCallback<Any>? = null

    fun setRootCallback(rootCallback: RootCallback<Any>) {
        this.rootCallback = rootCallback
    }

    private var products: MutableList<Food>? = null
    fun setData(products: MutableList<Food>?) {
        this.products = products
        notifyDataSetChanged()
    }

    inner class ViewHolder(var binding: View) :
        RecyclerView.ViewHolder(binding.rootView) {

        fun bind(data: Food?, position: Int) {

            data?.let {

                val image = it.images
                if (image != null && image.isNotEmpty()) {
                    GlideUtils.loadImageFullWidth(binding.ivFoodImage, image.get(0))
                }

                binding.tvPrice.text = AppUtil.getRupee(data.price)
                binding.tvFoodName.text = data.name
            }


        }
    }


}

