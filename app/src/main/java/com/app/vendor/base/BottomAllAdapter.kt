package com.app.vendor.base

/*
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.vendor.R
import com.mobcoder.kitchen.base.BottomSheetType
import kotlinx.android.synthetic.main.item_bottom_sheet.view.*
*/

//class BottomAllAdapter(
/*    private val type: BottomSheetType?,
    private val iBottomSheetClickListener: IBottomSheetClickListener
) : RecyclerView.Adapter<BottomAllAdapter.ViewHolder>() {

    private var list: List<MediaStore.Audio.Media>? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater =
            LayoutInflater.from(parent.context)
        val binding: View = layoutInflater.inflate(
            R.layout.item_bottom_sheet,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(list?.get(position), position)
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    fun updateData(list: List<MediaStore.Audio.Media>?) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(var binding: View) :
        RecyclerView.ViewHolder(binding.rootView) {
        fun bind(sheetModel: MediaStore.Audio.Media?, position: Int) {
            binding.tvBottomSheet.text = sheetModel?.name
            binding.llBottomSheet.setOnClickListener {
                iBottomSheetClickListener.onBottomSheetItemClicked(
                    position,
                    type,
                    sheetModel
                )
            }
        }
    }*/
//}