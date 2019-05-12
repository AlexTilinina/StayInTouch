package ru.kpfu.itis.stayintouch.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Attachment(
        var id: Int?,
        var name: String?,
        var url: String?,
        var label: String?,
        var owner: Int?,
        var attached_to: Int?,
        var uploaded: String?
) : Parcelable