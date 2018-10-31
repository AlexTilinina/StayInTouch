package ru.kpfu.itis.stayintouch.model

class Comment(
    var id: String = "",
    var author: User? = null,
    var text: String = ""
)