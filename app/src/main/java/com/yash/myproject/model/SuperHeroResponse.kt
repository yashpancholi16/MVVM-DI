package com.yash.myproject.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SuperHeroResponse(
    val code: Int,
    val etag: String,
    val data: Data):Parcelable