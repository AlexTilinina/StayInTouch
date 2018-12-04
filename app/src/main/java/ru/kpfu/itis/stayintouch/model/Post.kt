package ru.kpfu.itis.stayintouch.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@Parcelize
class Post(var id: Int? = null,
           var author: User? = null,
           var text: String = "",
           var created : String? = null,
           var dateEvent: GregorianCalendar? = null,
           //Приложения
           var tags: List<Tag> = ArrayList(),
           var comments: List<Comment> = ArrayList()
) : Parcelable {

    fun getDateCreated() : GregorianCalendar {
        val format = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSSSS")
        val date = format.parse(created)
        val dateCreated = GregorianCalendar()
        dateCreated.time = date
        return dateCreated
    }
}