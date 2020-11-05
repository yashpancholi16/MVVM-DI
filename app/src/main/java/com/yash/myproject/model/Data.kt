package com.yash.myproject.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: ArrayList<Character>
):Parcelable