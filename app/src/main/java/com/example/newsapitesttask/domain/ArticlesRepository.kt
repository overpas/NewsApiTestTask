package com.example.newsapitesttask.domain

interface ArticlesRepository {

    suspend fun getNewestArticles(): Result<List<Article>>

    suspend fun getArticleById(id: Long): Article
}
