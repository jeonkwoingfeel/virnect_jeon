package com.example.virnect_jeon.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class ResultGetSearchBooks(
    var lastBuildDate: String,
    var total: Int,
    var start: Int,
    var display: Int,
    var items: List<NaverBookItem>
)

@Parcelize
data class NaverBookItem(
    var title: String,
    var link: String,
    var image: String,
    var author: String,
    var price: String,
    var discount: String,
    var publisher: String,
    var pubdate: String,
    var isbn: String,
    var description: String
): Parcelable