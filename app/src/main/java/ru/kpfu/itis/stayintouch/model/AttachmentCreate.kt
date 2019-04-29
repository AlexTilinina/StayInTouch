package ru.kpfu.itis.stayintouch.model

import okhttp3.MultipartBody
import okhttp3.RequestBody

data class AttachmentCreate(
    val file: MultipartBody.Part,
    val label: RequestBody,
    var attach_to: RequestBody? = null
)