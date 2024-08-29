package com.example.newsapitesttask.domain

import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify

class GetNewArticlesTest {

    private val articlesRepository: ArticlesRepository = mock()

    private val getNewArticles = GetNewArticles(articlesRepository)

    @Test
    fun `getNewArticles returns articles from repository`() = runTest {
        getNewArticles()

        verify(articlesRepository).getNewestArticles()
    }
}
