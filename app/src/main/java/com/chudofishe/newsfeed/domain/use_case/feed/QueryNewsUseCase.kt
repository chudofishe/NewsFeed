package com.chudofishe.newsfeed.domain.use_case.feed

import androidx.paging.PagingSource
import com.chudofishe.newsfeed.domain.model.Article
import com.chudofishe.newsfeed.domain.repository.NewArticlesRepository
import javax.inject.Inject

class QueryNewsUseCase @Inject constructor(private val repository: NewArticlesRepository) {
    operator fun invoke(query: String): PagingSource<Int, Article> {
        return repository.getRecentNews(query)
    }
}