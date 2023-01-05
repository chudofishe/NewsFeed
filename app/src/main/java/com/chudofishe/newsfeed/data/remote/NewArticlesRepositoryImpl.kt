package com.chudofishe.newsfeed.data.remote

import androidx.paging.PagingSource
import com.chudofishe.newsfeed.domain.model.Article
import com.chudofishe.newsfeed.domain.repository.NewArticlesRepository
import javax.inject.Inject

class NewArticlesRepositoryImpl @Inject constructor(
    private val newsPagingSource: NewsPagingSource.Factory
): NewArticlesRepository {
    override fun getRecentNews(query: String): PagingSource<Int, Article> {
        return newsPagingSource.create(query)
    }
}