package com.example.newsapitesttask.articles

import app.cash.turbine.test
import com.example.newsapitesttask.domain.GetNewArticles
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class ArticlesViewModelImplTest {

    private val getNewArticles: GetNewArticles = mock()

    private val testDispatcher = StandardTestDispatcher()

    private val viewModel: ArticlesViewModel by lazy {
        ArticlesViewModelImpl(
            getNewArticles = getNewArticles,
            initialDelay = 10,
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
    fun `initial state is Loading`() {
        assertEquals(ArticlesState.Loading, viewModel.state.value)
    }

    @Test
    fun `initial state is Loading then Data`() = runTest {
        val dataState = ArticlesState.Data(persistentListOf())
        whenever(getNewArticles()).thenReturn(Result.success(emptyList()))

        viewModel.state.test {
            assertEquals(ArticlesState.Loading, awaitItem())
            assertEquals(dataState, awaitItem())
        }
    }

    @Test
    fun `initial state is Loading then Error`() = runTest {
        val exception = IllegalStateException("Test Exception")
        val errorState = ArticlesState.Error(exception)
        whenever(getNewArticles()).thenReturn(Result.failure(exception))

        viewModel.state.test {
            assertEquals(ArticlesState.Loading, awaitItem())
            assertEquals(errorState, awaitItem())
        }
    }
}
