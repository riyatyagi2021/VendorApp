package com.app.vendor.ui.home.kitchen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.vendor.R

class FoodAdapter(private var myList: List<Fooditems>):
    RecyclerView.Adapter<FoodAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view= LayoutInflater.from(parent.context).inflate(R.layout.activity_fooditems,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.foodimage.setImageResource(myList[position].image1)
        holder.foodName.text=myList[position].tv1
        holder.tvtime.text=myList[position].tv2
        holder.tvMsg.text=myList[position].tv3

    }

    override fun getItemCount(): Int {
        return myList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var foodimage: ImageView =itemView.findViewById(R.id.ivFooditem)
        var foodName: TextView =itemView.findViewById(R.id.foodname)
        var tvtime: TextView =itemView.findViewById(R.id.tvprice)
        var tvMsg: TextView =itemView.findViewById(R.id.ivFoodList)

    }
}