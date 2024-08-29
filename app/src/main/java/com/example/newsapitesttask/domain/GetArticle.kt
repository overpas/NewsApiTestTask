package com.example.newsapitesttask.domain

import javax.inject.Inject

class GetArticle @Inject constructor(
    private val articlesRepository: ArticlesRepository,
) {

    suspend operator fun invoke(id: Long): Article =
        articlesRepository.getArticleById(id)
}
