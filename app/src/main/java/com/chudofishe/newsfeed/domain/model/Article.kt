package com.chudofishe.newsfeed.domain.model

import com.google.gson.annotations.SerializedName

data class Article(
//    val category: List<String>,
//    val content: String?,
//    val country: List<String>,
//    val description: String,
//    val imageUrl: String?,
//    val pubDate: String,
//    val sourceId: String,
//    val link: String,
//    val title: String,
    val title: String,
    val url: String?,
    val description: String?,
    val author: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?,
)
