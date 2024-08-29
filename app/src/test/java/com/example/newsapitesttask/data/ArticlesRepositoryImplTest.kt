package com.example.newsapitesttask.data

import com.example.newsapitesttask.data.db.ArticlesDao
import com.example.newsapitesttask.data.db.entity.ArticleEntity
import com.example.newsapitesttask.data.db.entity.toArticle
import com.example.newsapitesttask.data.network.NewsApi
import com.example.newsapitesttask.data.network.response.ArticleResponse
import com.example.newsapitesttask.data.network.response.NewsResponse
import com.example.newsapitesttask.data.network.response.toArticle
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDateTime
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.times
import org.mockito.kotlin.whenever

class ArticlesRepositoryImplTest {

    private val newsApi: NewsApi = mock()
    private val articlesDao: ArticlesDao = mock()

    private val repository = ArticlesRepositoryImpl(newsApi, articlesDao)

    @Test
    fun `getNewestArticles fetches from network and inserts new articles`() = runTest {
        val articleResponse = ArticleResponse(
            author = "John Doe",
            title = "Sample Article",
            description = "This is a sample article",
            url = "https://example.com/article",
            urlToImage = "https://example.com/image.jpg",
            publishedAt = LocalDateTime.parse("2023-10-26T12:00:00"),
            content = "Sample content",
        )
        val newsResponse = NewsResponse(
            status = "OK",
            totalResults = 1,
            articles = listOf(articleResponse),
        )
        val articleEntity = ArticleEntity.from(articleResponse.toArticle())

        whenever(newsApi.getEverything()).thenReturn(Result.success(newsResponse))
        whenever(articlesDao.findAllArticlesOrderByDateDesc()).thenReturn(listOf(articleEntity))

        repository.getNewestArticles()

        verify(newsApi).getEverything()
        verify(articlesDao).insertArticle(articleEntity)
    }

    @Test
    fun `getNewestArticles fetches from network and does not insert invalid articles`() = runTest {
        val articleResponses = listOf(
            ArticleResponse(
                author = "John Doe",
                title = "Sample Article",
                description = "This is a sample article",
                url = "https://example.com/article",
                urlToImage = "https://example.com/image.jpg",
                publishedAt = LocalDateTime.parse("2023-10-26T12:00:00"),
                content = "Sample content",
            ),
            ArticleResponse(
                author = "John Doe",
                title = "[Removed]",
                description = "This is a sample article",
                url = "https://example.com/article",
                urlToImage = "https://example.com/image.jpg",
                publishedAt = LocalDateTime.parse("2023-10-26T12:00:00"),
                content = "Sample content",
            ),
            ArticleResponse(
                author = null,
                title = "Sample Article",
                description = "This is a sample article",
                url = "https://example.com/article",
                urlToImage = "https://example.com/image.jpg",
                publishedAt = LocalDateTime.parse("2023-10-26T12:00:00"),
                content = "Sample content",
            ),
            ArticleResponse(
                author = "John Doe",
                title = null,
                description = "This is a sample article",
                url = "https://example.com/article",
                urlToImage = "https://example.com/image.jpg",
                publishedAt = LocalDateTime.parse("2023-10-26T12:00:00"),
                content = "Sample content",
            ),
        )
        val newsResponse = NewsResponse(
            status = "OK",
            totalResults = 4,
            articles = articleResponses,
        )
        val articleEntities = articleResponses.map(ArticleResponse::toArticle)
            .map(ArticleEntity::from)

        whenever(newsApi.getEverything()).thenReturn(Result.success(newsResponse))
        whenever(articlesDao.findAllArticlesOrderByDateDesc()).thenReturn(articleEntities)

        repository.getNewestArticles()

        verify(newsApi).getEverything()
        verify(articlesDao).insertArticle(articleEntities[0])
        verify(articlesDao, times(0)).insertArticle(articleEntities[1])
        verify(articlesDao, times(0)).insertArticle(articleEntities[2])
        verify(articlesDao, times(0)).insertArticle(articleEntities[3])
    }

    @Test
    fun `getNewestArticles returns articles from database`() = runTest {
        val articleResponse = ArticleResponse(
            author = "John Doe",
            title = "Sample Article",
            description = "This is a sample article",
            url = "https://example.com/article",
            urlToImage = "https://example.com/image.jpg",
            publishedAt = LocalDateTime.parse("2023-10-26T12:00:00"),
            content = "Sample content",
        )
        val newsResponse = NewsResponse(
            status = "OK",
            totalResults = 1,
            articles = listOf(articleResponse),
        )
        val expectedArticle = articleResponse.toArticle()
        val articleEntity = ArticleEntity.from(expectedArticle)

        whenever(newsApi.getEverything()).thenReturn(Result.success(newsResponse))
        whenever(articlesDao.findAllArticlesOrderByDateDesc()).thenReturn(listOf(articleEntity))

        val result = repository.getNewestArticles()

        assertEquals(Result.success(listOf(expectedArticle)), result)
    }

    @Test
    fun `getArticleById returns correct article`() = runTest {
        val articleId = 1L
        val articleEntity = ArticleEntity(
            id = articleId,
            author = "Test Author",
            title = "Test Title",
            description = "Test Description",
            url = "https://example.com",
            urlToImage = "https://example.com/image.jpg",
            publishedAt = LocalDateTime.parse("2023-10-26T12:00:00"),
            content = "Test Content",
        )
        val expectedArticle = articleEntity.toArticle()

        whenever(articlesDao.findArticleById(articleId)).thenReturn(articleEntity)

        val result = repository.getArticleById(articleId)

        assertEquals(expectedArticle, result)
    }
}
