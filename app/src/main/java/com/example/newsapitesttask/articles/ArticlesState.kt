package com.example.newsapitesttask.articles

import androidx.compose.runtime.Immutable
import com.example.newsapitesttask.domain.Article
import kotlinx.collections.immutable.ImmutableList

internal sealed class ArticlesState {

    data object Loading : ArticlesState()

    data class Data(
        val articles: ImmutableList<Article>,
    ): ArticlesState()

    @Immutable
    data class Error(val error: Throwable): ArticlesState()
}
