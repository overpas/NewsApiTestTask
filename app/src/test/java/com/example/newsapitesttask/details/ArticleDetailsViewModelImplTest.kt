package com.example.newsapitesttask.details

import app.cash.turbine.test
import com.example.newsapitesttask.domain.Article
import com.example.newsapitesttask.domain.GetArticle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.LocalDateTime
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class ArticleDetailsViewModelImplTest {

    private val getArticle: GetArticle = mock()
    private val id = 1L

    private val testDispatcher = StandardTestDispatcher()

    private val viewModel: ArticleDetailsViewModel by lazy {
        ArticleDetailsViewModelImpl(
            getArticle = getArticle,
            initialDelay = 10L,
            id = id,
        )
    }

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is Initial`() {
        assertEquals(ArticleDetailsState.Initial, viewModel.state.value)
    }

    @Test
    fun `initial state is Initial then Details`() = runTest {
        val article = Article(
            id = id,
            author = "Test Author",
            title = "Test Title",
            description = "Test Description",
            url = "https://example.com",
            urlToImage = "https://example.com/image.jpg",
            publishedAt = LocalDateTime.parse("2023-04-01T12:00:00"),
            content = "Test Content",
        )
        whenever(getArticle(id)).thenReturn(article)

        viewModel.state.test {
            assertEquals(ArticleDetailsState.Initial, awaitItem())
            assertEquals(ArticleDetailsState.Details(article), awaitItem())
        }
    }
}
