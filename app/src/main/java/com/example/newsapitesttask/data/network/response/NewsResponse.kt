package com.example.newsapitesttask.data.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class NewsResponse(
    @SerialName("status")
    val status: String,
    @SerialName("totalResults")
    val totalResults: Int,
    @SerialName("articles")
    val articles: List<ArticleResponse> = emptyList(),
)
