package com.example.translator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.number_list.view.*

class TranslatedItemsAdapter(private val items: ArrayList<TranslatedItem>, private val layoutId: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class MyRecyclerViewHolder (view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyRecyclerViewHolder(
        LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
    )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        holder.itemView.englishTextView.text = item.english
        holder.itemView.originalTextView.text = item.original
    }

    override fun getItemCount() = items.size
}