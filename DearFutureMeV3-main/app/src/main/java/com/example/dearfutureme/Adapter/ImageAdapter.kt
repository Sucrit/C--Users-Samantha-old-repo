package com.example.dearfutureme.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dearfutureme.Model.Image
import com.example.dearfutureme.R
import com.example.dearfutureme.databinding.ViewholderImageBinding

class ImageAdapter(private val imageList: List<Image>) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

        class ImageViewHolder(private val binding: ViewholderImageBinding) : RecyclerView.ViewHolder(binding.root) {
                fun bind(image: Image) {
                        // Load image using Glide or any other image loading library
                        Glide.with(itemView.context)
                                .load(image.image) // Assuming 'image' has a property 'image' that holds the URL or URI
                                .into(binding.imageViewHolder) // Ensure you have an ImageView with this ID in your layout
                }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
                val binding = ViewholderImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ImageViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
                val image = imageList[position]
                holder.bind(image)
        }

        override fun getItemCount(): Int {
                return imageList.size
        }
}
