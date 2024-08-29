package com.example.newsapitesttask.data

import com.example.newsapitesttask.data.db.ArticlesDao
import com.example.newsapitesttask.data.db.entity.ArticleEntity
import com.example.newsapitesttask.data.db.entity.toArticle
import com.example.newsapitesttask.data.network.NewsApi
import com.example.newsapitesttask.data.network.response.ArticleResponse
import com.example.newsapitesttask.data.network.response.toArticle
import com.example.newsapitesttask.domain.Article
import com.example.newsapitesttask.domain.ArticlesRepository
import javax.inject.Inject

internal class ArticlesRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi,
    private val articlesDao: ArticlesDao,
) : ArticlesRepository {

    override suspend fun getNewestArticles(): Result<List<Article>> {
        val newArticlesResult = newsApi.getEverything()
            .map { news ->
                news.articles.map(ArticleResponse::toArticle)
            }
        newArticlesResult.onSuccess { articles ->
            insertNewArticles(articles.map(ArticleEntity::from))
        }
        return Result.success(
            articlesDao.findAllArticlesOrderByDateDesc().map(ArticleEntity::toArticle)
        )
    }

    private suspend fun insertNewArticles(articles: List<ArticleEntity>) {
        articles.forEach { article ->
            if (article.author != null && article.title != null && article.title != "[Removed]") {
                val existing = articlesDao.findArticleByAuthorAndTitle(
                    author = article.author,
                    title = article.title,
                )
                if (existing == null) {
                    articlesDao.insertArticle(article)
                }
            }
        }
    }

    override suspend fun getArticleById(id: Long): Article =
        articlesDao.findArticleById(id)
            .toArticle()
}
