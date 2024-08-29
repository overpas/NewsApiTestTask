package com.example.newsapitesttask.domain

import javax.inject.Inject

internal class GetNewArticles @Inject constructor(
    private val articlesRepository: ArticlesRepository,
) {

    suspend operator fun invoke(): Result<List<Article>> =
        articlesRepository.getNewestArticles()
}
