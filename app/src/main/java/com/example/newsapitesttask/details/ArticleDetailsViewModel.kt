package com.example.newsapitesttask.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapitesttask.di.InitialDelay
import com.example.newsapitesttask.domain.GetArticle
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

internal interface ArticleDetailsViewModel {

    val state: StateFlow<ArticleDetailsState>
}

@HiltViewModel(assistedFactory = ArticleDetailsViewModelImpl.Factory::class)
internal class ArticleDetailsViewModelImpl @AssistedInject constructor(
    getArticle: GetArticle,
    @InitialDelay initialDelay: Long,
    @Assisted id: Long,
) : ViewModel(), ArticleDetailsViewModel {

    override val state: StateFlow<ArticleDetailsState> =
        flow {
            delay(initialDelay)
            val article = getArticle(id)
            emit(ArticleDetailsState.Details(article))
        }.stateIn(viewModelScope, SharingStarted.Lazily, ArticleDetailsState.Initial)

    @AssistedFactory
    interface Factory {
        fun create(id: Long): ArticleDetailsViewModelImpl
    }
}
