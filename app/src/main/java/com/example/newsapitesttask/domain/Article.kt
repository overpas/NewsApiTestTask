package com.example.newsapitesttask.domain

import kotlinx.datetime.LocalDateTime

data class Article(
    val id: Long,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: LocalDateTime?,
    val content: String?,
)
