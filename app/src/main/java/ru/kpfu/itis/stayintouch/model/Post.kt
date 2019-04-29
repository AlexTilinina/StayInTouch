package ru.kpfu.itis.stayintouch.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*
import kotlin.collections.ArrayList

@Parcelize
data class Post(var id: Int? = null,
           var author: User? = null,
           var text: String = "",
           var created : String? = null,
           var dateEvent: GregorianCalendar? = null,
           var attachments: List<Attachment> = ArrayList(),
           var tags: List<Tag> = ArrayList(),
           var comments: List<Comment>? = ArrayList()
) : Parcelable