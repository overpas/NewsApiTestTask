package com.example.newsapitesttask.articles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapitesttask.di.InitialDelay
import com.example.newsapitesttask.domain.GetNewArticles
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


internal interface ArticlesViewModel {

    val state: StateFlow<ArticlesState>
}

@HiltViewModel
internal class ArticlesViewModelImpl @Inject constructor(
    getNewArticles: GetNewArticles,
    @InitialDelay initialDelay: Long,
) : ViewModel(), ArticlesViewModel {

    override val state: StateFlow<ArticlesState> = flow {
        delay(initialDelay)
        getNewArticles()
            .onSuccess { articles ->
                emit(ArticlesState.Data(articles.toImmutableList()))
            }
            .onFailure { error ->
                emit(ArticlesState.Error(error))
            }
    }.stateIn(viewModelScope, SharingStarted.Lazily, ArticlesState.Loading)
}
