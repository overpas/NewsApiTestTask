package com.example.newsapitesttask.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsapitesttask.domain.Article
import kotlinx.datetime.LocalDateTime

@Entity(tableName = "articles")
internal data class ArticleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "author")
    val author: String?,
    @ColumnInfo(name = "title")
    val title: String?,
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "url")
    val url: String?,
    @ColumnInfo(name = "urlToImage")
    val urlToImage: String?,
    @ColumnInfo(name = "publishedAt")
    val publishedAt: LocalDateTime?,
    @ColumnInfo(name = "content")
    val content: String?,
) {

    companion object {

        fun from(article: Article): ArticleEntity =
            ArticleEntity(
                id = article.id,
                author = article.author,
                title = article.title,
                description = article.description,
                url = article.url,
                urlToImage = article.urlToImage,
                publishedAt = article.publishedAt,
                content = article.content,
            )
    }
}

internal fun ArticleEntity.toArticle(): Article =
    Article(
        id = id,
        author = author,
        title = title,
        description = description,
        url = url,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = content,
    )
