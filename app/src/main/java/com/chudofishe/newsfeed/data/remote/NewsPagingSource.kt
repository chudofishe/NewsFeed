package com.chudofishe.newsfeed.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chudofishe.newsfeed.data.remote.dto.toArticle
import com.chudofishe.newsfeed.domain.model.Article
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import retrofit2.HttpException

class NewsPagingSource @AssistedInject constructor(
    @Assisted("query" )private val query: String,
    private val newsService: NewsApiService
    ) : PagingSource<Int, Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        try {
            val query = this.query.ifBlank { null }
            val pageNumber = params.key ?: INITIAL_PAGE_NUMBER
            val pageSize = params.loadSize.coerceAtMost(NewsApiService.MAX_PAGE_SIZE)
            val response = newsService.news(query = query, page = pageNumber, pageSize = pageSize)

            return if (response.isSuccessful) {
                val articles = response.body()!!.articles.map { it.toArticle() }
                val nextPageNumber = if (articles.isEmpty()) null else pageNumber + 1
                val prevPageNumber = if (pageNumber > 1) pageNumber - 1 else null
                LoadResult.Page(articles, prevPageNumber, nextPageNumber)
            } else {
                LoadResult.Error(HttpException(response))
            }
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted("query") query: String): NewsPagingSource
    }

    companion object {
        const val INITIAL_PAGE_NUMBER = 1
    }
}