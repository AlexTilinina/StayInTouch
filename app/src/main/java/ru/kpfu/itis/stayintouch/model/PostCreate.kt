package ru.kpfu.itis.stayintouch.model

data class PostCreate(val text: String,
                 val add_tags: List<String>)