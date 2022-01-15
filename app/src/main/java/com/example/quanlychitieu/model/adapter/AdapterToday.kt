package com.example.quanlychitieu.model.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quanlychitieu.R
import com.example.quanlychitieu.model.Item

class AdapterToday(private var listItemToday : ArrayList<Item>) : RecyclerView.Adapter<AdapterToday.ViewHoder>() {
    inner class ViewHoder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var itemImage : ImageView
        var itemMoney : TextView
        var itemName : TextView
        var itemDetail : TextView

        init {
            itemImage = itemView.findViewById(R.id.im_spend_today)
            itemDetail = itemView.findViewById(R.id.tv_item_detail)
            itemName = itemView.findViewById(R.id.tv_item_type)
            itemMoney = itemView.findViewById(R.id.tv_item_money)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterToday.ViewHoder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_today, parent, false)
        return ViewHoder(v)
    }

    override fun onBindViewHolder(holder: AdapterToday.ViewHoder, position: Int) {
        val currentItem = listItemToday[position]
        holder.itemMoney.text = currentItem.money.toString()
        holder.itemName.text = currentItem.name
        holder.itemDetail.text = currentItem.detail
        if (currentItem.type.equals("Thu nhập")) {
            holder.itemImage.setImageResource(R.drawable.ic_income)
        } else {
            holder.itemImage.setImageResource(R.drawable.ic_outcome)
        }

    }

    override fun getItemCount(): Int {
        return listItemToday.size
    }
}