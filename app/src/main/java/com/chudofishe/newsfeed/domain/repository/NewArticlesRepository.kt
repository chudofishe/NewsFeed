package com.chudofishe.newsfeed.domain.repository

import androidx.paging.PagingSource
import com.chudofishe.newsfeed.domain.model.Article

interface NewArticlesRepository {
    fun getRecentNews(query: String): PagingSource<Int, Article>
}