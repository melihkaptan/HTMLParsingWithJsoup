package com.example.htmlparsingwithjsoup.adapters

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.htmlparsingwithjsoup.model.OnItemClickListener
import com.example.htmlparsingwithjsoup.R
import com.example.htmlparsingwithjsoup.model.Cut

class ItemListViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    fun bindItems(itemModel: Cut , clickListener: OnItemClickListener) {
        val header = itemView.findViewById(R.id.header) as TextView
        val description = itemView.findViewById(R.id.description) as TextView

        header.text = itemModel.header
        description.text = itemModel.description

        itemView.setOnClickListener {
            clickListener.onItemClicked(itemModel)
        }
    }
}