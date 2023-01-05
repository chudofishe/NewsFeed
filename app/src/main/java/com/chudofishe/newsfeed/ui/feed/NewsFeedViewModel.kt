package com.chudofishe.newsfeed.ui.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.chudofishe.newsfeed.domain.model.Article
import com.chudofishe.newsfeed.domain.use_case.feed.QueryNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class NewsFeedViewModel @Inject constructor(
    private val queryNewsUseCase: QueryNewsUseCase
) : ViewModel() {

    private val _query = MutableStateFlow("Android")
    val query: StateFlow<String> = _query.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val news: StateFlow<PagingData<Article>> = query
        .map(::newPager)
        .flatMapLatest { pager -> pager.flow }
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    private fun newPager(query: String): Pager<Int, Article> {
        return Pager(PagingConfig(pageSize = 5, enablePlaceholders = false)) {
            queryNewsUseCase(query)
        }
    }

}