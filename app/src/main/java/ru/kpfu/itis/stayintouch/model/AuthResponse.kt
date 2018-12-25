package ru.kpfu.itis.stayintouch.model

data class AuthResponse(val token: String,
                   val user: User)