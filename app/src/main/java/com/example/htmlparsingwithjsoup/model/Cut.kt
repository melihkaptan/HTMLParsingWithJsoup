package com.example.htmlparsingwithjsoup.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Cut (
    var header: String? = null,
    var description: String? = null,
    var linkText: String? = null
) : Parcelable