package com.chudofishe.newsfeed.data.remote.dto


import androidx.annotation.IntRange
import com.google.gson.annotations.SerializedName

data class ResponseDto(
    @SerializedName("status") val status: String,
    @SerializedName("totalResults") @IntRange(from = 1) val totalResults: Int,
    @SerializedName("message") val message: String? = null,
    @SerializedName("articles") val articles: List<ArticleDto>,
)