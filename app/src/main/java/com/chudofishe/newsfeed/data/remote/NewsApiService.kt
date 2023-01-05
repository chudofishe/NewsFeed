package com.chudofishe.newsfeed.data.remote

import androidx.annotation.IntRange
import com.chudofishe.newsfeed.data.remote.dto.ResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface NewsApiService {

    @GET("/v2/everything")
    suspend fun news(
        @Query("q") query: String? = null,
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("pageSize") @IntRange(from = 1, to = MAX_PAGE_SIZE.toLong()) pageSize: Int = DEFAULT_PAGE_SIZE,
        @Query("qInTitle") queryInBody: String? = null,
        @Query("sources") sources: String? = null,
        @Query("domains") domains: String? = null,
        @Query("excludeDomains") excludeDomains: String? = null,
        @Query("from") from: Date? = null,
        @Query("to") to: Date? = null,
        @Query("language") language: Language? = null,
    ): Response<ResponseDto>

    enum class Language {
        ru, en
    }

    enum class Category {
        entertainment, sports, technology
    }

    companion object {
        const val DEFAULT_PAGE_SIZE = 20
        const val MAX_PAGE_SIZE = 20
    }
}