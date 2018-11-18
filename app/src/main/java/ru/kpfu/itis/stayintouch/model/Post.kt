package ru.kpfu.itis.stayintouch.model

import java.util.*
import kotlin.collections.ArrayList

class Post(var id: Int? = null,
           var author: User? = null,
           var text: String = "",
           var date: GregorianCalendar? = null,
           //Приложения
           var tags: List<Tag> = ArrayList())