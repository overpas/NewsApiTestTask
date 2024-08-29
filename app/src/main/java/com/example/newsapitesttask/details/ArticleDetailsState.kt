package com.example.newsapitesttask.details

import com.example.newsapitesttask.domain.Article

internal sealed class ArticleDetailsState {

    data object Initial : ArticleDetailsState()

    data class Details(
        val article: Article,
    ) : ArticleDetailsState()
}
