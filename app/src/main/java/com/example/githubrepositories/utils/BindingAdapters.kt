package com.example.githubrepositories.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.githubrepositories.R
import com.squareup.picasso.Picasso


@BindingAdapter("imageUrl")
fun setImageUrl(view: ImageView, imageUrl: String) {
    Picasso.get()
        .load(imageUrl)
        .placeholder(R.drawable.img_holder)
        .into(view)
}