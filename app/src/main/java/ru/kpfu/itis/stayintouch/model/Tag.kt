package ru.kpfu.itis.stayintouch.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tag(
    var id: Int,
    var name: String = "",
    var subscr: Boolean = false
) : Parcelable