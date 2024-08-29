package com.example.newsapitesttask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.newsapitesttask.articles.ArticlesScreen
import com.example.newsapitesttask.articles.ArticlesViewModelImpl
import com.example.newsapitesttask.details.ArticleDetailsScreen
import com.example.newsapitesttask.details.ArticleDetailsViewModelImpl
import com.example.newsapitesttask.ui.theme.NewsApiTestTaskTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsApiTestTaskTheme {
                NewsApiTestTaskApp(Modifier.fillMaxSize())
            }
        }
    }
}

object Screens {

    @Serializable
    data object Articles

    @Serializable
    data class ArticleDetails(
        val articleId: Long,
    )
}

@Composable
fun NewsApiTestTaskApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.Articles,
        modifier = modifier,
    ) {
        composable<Screens.Articles> {
            ArticlesScreen(
                viewModel = hiltViewModel<ArticlesViewModelImpl>(),
                onArticleClick = { article ->
                    navController.navigate(Screens.ArticleDetails(article.id))
                },
                modifier = Modifier.fillMaxSize(),
            )
        }
        composable<Screens.ArticleDetails> { entry ->
            val args = entry.toRoute<Screens.ArticleDetails>()
            val uriHandler = LocalUriHandler.current
            ArticleDetailsScreen(
                viewModel = hiltViewModel<ArticleDetailsViewModelImpl, ArticleDetailsViewModelImpl.Factory> { factory ->
                    factory.create(args.articleId)
                },
                onNavigateUp = {
                    navController.navigateUp()
                },
                onReadMoreClick = { article ->
                    article.url?.let(uriHandler::openUri)
                },
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}
