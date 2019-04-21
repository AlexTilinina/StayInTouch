package ru.kpfu.itis.stayintouch.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*
import kotlin.collections.ArrayList

@Parcelize
data class Comment(
    var id: String = "",
    var author: User? = null,
    var text: String = "",
    var date: GregorianCalendar?,
    var news_commented: Int? = null,
    var answers: List<Comment> = ArrayList(),
    var like_counter: Int? = null,
    var answer_to:Int? = null) : Parcelable