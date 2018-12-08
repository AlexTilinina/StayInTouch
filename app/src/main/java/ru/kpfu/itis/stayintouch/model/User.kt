package ru.kpfu.itis.stayintouch.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User(var id: Int?,
           var first_name: String = "",
           var last_name: String = "",
           var username: String = "",
           var email: String = "",
           var profile: Profile? = null,
           var news: List<Post>? = ArrayList(),
           var comments: List<Comment>? = ArrayList()
    ) : Parcelable