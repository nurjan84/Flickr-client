package com.nurzhan.flickr.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nurzhan.flickr.R
import com.nurzhan.flickr.callbacks.Callback
import com.nurzhan.flickr.glide.GlideApp
import com.nurzhan.flickr.room.entities.Photo
import com.nurzhan.flickr.utils.Utils
import kotlinx.android.synthetic.main.item_photo.view.*

class PhotosAdapter (private val context:Context, private val viewSize:Int) : PagedListAdapter<Photo, RecyclerView.ViewHolder>(
    object : DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean =
        oldItem.photoId == newItem.photoId
    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean =
        oldItem == newItem
}){

    private lateinit var photoClickListener: Callback
    fun setOnPhotoClickListener(callback: Callback){
        photoClickListener = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_photo, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val holder = viewHolder as Holder
        holder.bindViews(getItem(position))
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        fun bindViews(photo:Photo?){
            if (photo != null){

                GlideApp.with(context)
                    .load("https://farm${photo.farm}.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}_m.jpg")
                    .into(itemView.imageView)

                val topMargin = if(adapterPosition < 3){ 2 }else{ 1 }
                val params = LinearLayout.LayoutParams(viewSize, viewSize )
                params.setMargins( Utils.dpToPx(context, 1), Utils.dpToPx(context, topMargin) , Utils.dpToPx(context, 1), Utils.dpToPx(context, 1))
                itemView.photoLayout.layoutParams = params

                itemView.imageView.setOnClickListener(this)
            }
        }
        override fun onClick(v: View?) {
            val pos = adapterPosition
            if(pos > RecyclerView.NO_POSITION){
                photoClickListener.process(getItem(pos))
            }
        }
    }
}