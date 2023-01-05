package com.chudofishe.newsfeed.data.remote.dto


import com.chudofishe.newsfeed.domain.model.Article
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName

data class ArticleDto(
    @SerializedName("source") val source: SourceDto? = null,
    @SerializedName("title") val title: String = "",
    @SerializedName("url") val url: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("author") val author: String? = null,
    @SerializedName("urlToImage") val urlToImage: String? = null,
    @SerializedName("publishedAt") val publishedAt: String? = null,
    @SerializedName("content") val content: String? = null,
)

fun ArticleDto.toArticle() = Article(
    title, url, description, author, urlToImage, publishedAt, content
)