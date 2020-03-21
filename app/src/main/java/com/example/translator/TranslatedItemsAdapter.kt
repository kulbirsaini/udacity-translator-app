package com.example.translator

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*
import kotlinx.android.synthetic.main.list_item.view.englishTextView
import kotlinx.android.synthetic.main.list_item.view.originalTextView
import kotlinx.android.synthetic.main.list_item.view.playAudio
import kotlinx.android.synthetic.main.phrase_list.view.*

class TranslatedItemsAdapter(private val items: ArrayList<TranslatedItem>,
                             private val layoutId: Int,
                             private val backgroundColor: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class MyRecyclerViewHolder (view: View) : RecyclerView.ViewHolder(view)

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyRecyclerViewHolder(
        LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
    )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        holder.itemView.englishTextView.text = item.english
        holder.itemView.originalTextView.text = item.original

        // Set avatar image only if this layout supports it
        if (item.imageId > 0)
            holder.itemView.avatar.setImageResource(item.imageId)

        // Set background color for various text views
        holder.itemView.englishTextView.setBackgroundResource(backgroundColor)
        holder.itemView.originalTextView.setBackgroundResource(backgroundColor)
        holder.itemView.playAudio.setBackgroundResource(backgroundColor)

        // Invert color for spacer for last items for a feel of continuity
        val hasSingleSpacer = holder.itemView.spacer != null
        val isLastItem = items.size - 1 == position

        if (hasSingleSpacer) {
            if (isLastItem) {
                holder.itemView.spacer.setBackgroundResource(backgroundColor)
            } else {
                holder.itemView.spacer.setBackgroundColor(Color.parseColor("#FFFFFF"))
            }
        } else {
            if (isLastItem) {
                holder.itemView.spacerRight.setBackgroundResource(backgroundColor)
                holder.itemView.spacerLeft.setBackgroundColor(Color.parseColor("#FFFFFF"))
            } else {
                holder.itemView.spacerLeft.setBackgroundResource(backgroundColor)
                holder.itemView.spacerRight.setBackgroundColor(Color.parseColor("#FFFFFF"))
            }
        }
    }
}