package ru.kpfu.itis.stayintouch.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Tag(var id: String? = null,
          var tag: String = "") : Parcelable