package dev.iamspathan.simpletodo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.iamspathan.simpletodo.ItemsAdapter.ItemViewHolder

class ItemsAdapter(val list: List<String>, val onItemLongClick: onItemLongClick, val onItemClick: onItemClick) : RecyclerView.Adapter<ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textView = itemView.findViewById<TextView>(android.R.id.text1)

        fun bind(item: String) {
            textView.text = item
            textView.setOnLongClickListener {
                onItemLongClick.onLongClick(adapterPosition)
                true
            }

            textView.setOnClickListener {
                onItemClick.onClick(adapterPosition)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount() = list.size
}


interface onItemLongClick {
    fun onLongClick(item: Int)
}

interface onItemClick {
    fun onClick(item: Int)
}