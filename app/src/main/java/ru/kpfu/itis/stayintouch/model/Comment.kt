package ru.kpfu.itis.stayintouch.model

import java.util.*

class Comment(
    var id: String = "",
    var author: User? = null,
    var text: String = "",
    var date: GregorianCalendar,
    var postId: Int? = null)