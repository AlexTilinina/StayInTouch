package ru.kpfu.itis.stayintouch.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User(var id: String? = null,
           var name: String = "",
           var surname: String = "",
           var profilePhoto: String? = null) : Parcelable