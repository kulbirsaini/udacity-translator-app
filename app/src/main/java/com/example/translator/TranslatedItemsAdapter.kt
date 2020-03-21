package com.example.translator

import android.content.Context
import android.graphics.Color
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*

class TranslatedItemsAdapter(private val items: ArrayList<TranslatedItem>,
                             private val layoutId: Int,
                             private val backgroundColor: Int, private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class MyRecyclerViewHolder (view: View) : RecyclerView.ViewHolder(view)

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyRecyclerViewHolder(
        LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
    )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]

        // Set text
        holder.itemView.englishTextView.text = item.english
        holder.itemView.originalTextView.text = item.original

        // Set background color for entire layout
        holder.itemView.listLayout.setBackgroundResource(backgroundColor)

        // Set avatar image only if this layout supports it
        if (item.imageId > 0) {
            // Set image resource and make avatar and left spacer visible
            holder.itemView.avatar.setImageResource(item.imageId)
            holder.itemView.avatar.visibility = View.VISIBLE
            holder.itemView.spacerLeft.visibility = View.VISIBLE

            // Make sure the avatar has a white background
            holder.itemView.avatar.setBackgroundColor(Color.parseColor("#FFFFFF"))
        } else {
            // Hide avatar and left spacer when there is no image for the list item
            holder.itemView.avatar.visibility = View.GONE
            holder.itemView.spacerLeft.visibility = View.GONE
        }

        // If we have a sound file, we need to bind the on click listener to play the audio
        if (item.soundId > 0) {
            holder.itemView.playAudio.setOnClickListener {
                MediaPlayer.create(context, item.soundId).start()
            }
        }

        // Invert color for spacers for last items for a feel of continuity
        if (items.size - 1 == position) {
            holder.itemView.spacerLeft.setBackgroundColor(Color.parseColor("#FFFFFF"))
        } else {
            holder.itemView.spacerRight.setBackgroundColor(Color.parseColor("#FFFFFF"))
        }

    }
}