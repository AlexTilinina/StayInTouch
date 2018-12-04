package ru.kpfu.itis.stayintouch.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Profile (val id: Int,
               val user: Int,
               //TODO изменить название на tags
               val tags_list: List<Tag>,
               val photo_url: String? = ""
    ) : Parcelable