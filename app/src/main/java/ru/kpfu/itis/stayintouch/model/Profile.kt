package ru.kpfu.itis.stayintouch.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Profile (val id: Int,
               val user: Int,
               val tags: List<Tag>? = ArrayList(),
               val photo_url: String? = ""
    ) : Parcelable