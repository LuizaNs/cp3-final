package com.example.cp3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.cp3.R
import com.squareup.picasso.Picasso

class ImageAdapter(private val imageUrls: List<String>) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        // Infla o layout do item (item_image)
        val view = LayoutInflater.from(parent.context).inflate(R.layout.imagem_item, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        // Carrega a imagem usando Picasso
        val imageUrl = imageUrls[position]
        Picasso.get().load(imageUrl).into(holder.imageView)
    }

    override fun getItemCount(): Int = imageUrls.size

    class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Referência para a ImageView dentro do layout do item
        val imageView = view.findViewById<ImageView>(R.id.imageView)
    }
}
