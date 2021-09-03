package com.app.vendor.roomdatabse

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.app.vendor.R
import kotlinx.android.synthetic.main.roomdb_first.view.*
import kotlinx.android.synthetic.main.roomdb_item.view.*

class RecyclerViewAdapter(val listener: ClickListener): RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    var items  = ArrayList<User>()

    fun setListData(data: ArrayList<User>) {
        this.items = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.roomdb_item, parent, false)
        return MyViewHolder(inflater, listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            listener.onItemClickListener(items[position])
        }

        holder.bind(items[position])

    }


    class MyViewHolder(view: View, val listener: ClickListener): RecyclerView.ViewHolder(view) {

        var tvFName: TextView =view.findViewById(R.id.fn)
        var tvLName:TextView=view.findViewById(R.id.ln)
        var deleteUserID:Button=view.findViewById(R.id.btDelete)


        fun bind(data: User) {

            tvFName.text = data.firstName
            tvLName.text = data.lastName
             deleteUserID.setOnClickListener {
                  listener.onDeleteUserClickListener(data)
             }


        }
    }

    interface ClickListener{
        fun onDeleteUserClickListener(user: User)
        fun onItemClickListener(user: User)
    }
}