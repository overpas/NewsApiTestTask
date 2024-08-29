package com.example.newsapitesttask.domain

import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify

class GetArticleTest {

    private val articlesRepository: ArticlesRepository = mock()

    private val getArticle = GetArticle(articlesRepository)

    @Test
    fun `getArticle returns article from repository`() = runTest {
        val id = 1L
        getArticle(id)

        verify(articlesRepository).getArticleById(id)
    }
}
