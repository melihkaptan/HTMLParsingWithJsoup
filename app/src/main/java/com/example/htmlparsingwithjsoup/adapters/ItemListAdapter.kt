package com.example.htmlparsingwithjsoup.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.htmlparsingwithjsoup.model.OnItemClickListener
import com.example.htmlparsingwithjsoup.R
import com.example.htmlparsingwithjsoup.model.Cut


class ItemListAdapter(val itemList: ArrayList<Cut>, val itemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<ItemListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListViewHolder {
        return ItemListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.cut_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ItemListViewHolder, position: Int) {
        holder.bindItems(itemList[position] , itemClickListener)
    }

}