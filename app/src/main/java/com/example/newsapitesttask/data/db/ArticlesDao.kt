package com.example.newsapitesttask.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.newsapitesttask.data.db.entity.ArticleEntity

@Dao
internal interface ArticlesDao {

    @Insert
    suspend fun insertArticles(articles: List<ArticleEntity>)

    @Insert
    suspend fun insertArticle(article: ArticleEntity)

    @Query("SELECT * FROM articles ORDER BY publishedAt DESC")
    suspend fun findAllArticlesOrderByDateDesc(): List<ArticleEntity>

    @Query("SELECT * FROM articles WHERE id = :id")
    suspend fun findArticleById(id: Long): ArticleEntity

    @Query("SELECT * FROM articles WHERE author = :author AND title = :title")
    suspend fun findArticleByAuthorAndTitle(
        author: String,
        title: String,
    ): ArticleEntity?
}
