package ru.kpfu.itis.stayintouch.utils

import android.net.Uri
import android.widget.ImageView
import com.squareup.picasso.Picasso
import ru.kpfu.itis.stayintouch.R
import java.io.File

class ImageLoadHelper {

    companion object {

        fun loadImage(picture: Int, into: ImageView, size: Int) {
            Picasso.get()
                .load(picture)
                .resize(size, size)
                .centerCrop()
                .noFade()
                .placeholder(R.drawable.ic_placeholder)
                .into(into)
        }

        fun loadImage(picture: String?, into: ImageView, size: Int) {
            if (!picture.isNullOrEmpty()) {
                Picasso.get()
                    .load(picture)
                    .resize(size, size)
                    .centerCrop()
                    .noFade()
                    .placeholder(R.drawable.ic_placeholder)
                    .into(into)
            } else {
                Picasso.get()
                    .load(R.drawable.ic_placeholder)
                    .resize(size, size)
                    .centerCrop()
                    .noFade()
                    .placeholder(R.drawable.ic_placeholder)
                    .into(into)
            }
        }

        fun loadImage(picture: File, into: ImageView, size: Int) {
            Picasso.get()
                .load(picture)
                .resize(size, size)
                .centerCrop()
                .noFade()
                .placeholder(R.drawable.ic_placeholder)
                .into(into)
        }

        fun loadImage(picture: Uri?, into: ImageView, size: Int) {
            Picasso.get()
                .load(picture)
                .resize(size, size)
                .centerCrop()
                .noFade()
                .placeholder(R.drawable.ic_placeholder)
                .into(into)
        }

        fun loadImage(picture: String?, into: ImageView, size: Int, error: Int) {
            if (picture.isNullOrEmpty()) {
                Picasso.get()
                    .load(R.drawable.ic_placeholder)
                    .resize(size, size)
                    .centerCrop()
                    .noFade()
                    .error(error)
                    .placeholder(R.drawable.ic_placeholder)
                    .into(into)
            } else {
                Picasso.get()
                    .load(picture)
                    .resize(size, size)
                    .centerCrop()
                    .noFade()
                    .error(error)
                    .placeholder(R.drawable.ic_placeholder)
                    .into(into)
            }
        }

        fun loadImage(picture: File, into: ImageView, size: Int, error: Int) {
            Picasso.get()
                .load(picture)
                .resize(size, size)
                .centerCrop()
                .noFade()
                .error(error)
                .placeholder(R.drawable.ic_placeholder)
                .into(into)
        }
    }
}