package ru.kpfu.itis.stayintouch.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*
import kotlin.collections.ArrayList

@Parcelize
class Post(var id: Int? = null,
           var author: User? = null,
           var text: String = "",
           var date : GregorianCalendar? = null,
           var dateEvent: GregorianCalendar? = null,
           //Приложения
           var tags: List<Tag> = ArrayList()) : Parcelable