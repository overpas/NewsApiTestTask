package com.example.newsapitesttask.articles

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.newsapitesttask.R
import com.example.newsapitesttask.domain.Article
import com.example.newsapitesttask.ui.orElseUnknown
import com.seiko.imageloader.rememberImagePainter
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ArticlesScreen(
    viewModel: ArticlesViewModel,
    onArticleClick: (Article) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
            )
        },
        modifier = modifier,
    ) { paddingValues ->
        Box(Modifier.padding(paddingValues)) {
            val state by viewModel.state.collectAsStateWithLifecycle()
            ArticlesScreenContent(
                state = state,
                onArticleClick = onArticleClick,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

@Composable
private fun ArticlesScreenContent(
    state: ArticlesState,
    onArticleClick: (Article) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (state) {
        is ArticlesState.Loading -> {
            ArticlesLoading(modifier)
        }

        is ArticlesState.Data -> {
            ArticlesList(
                articles = state.articles,
                onArticleClick = onArticleClick,
                modifier = modifier,
            )
        }

        is ArticlesState.Error -> {
            ArticlesError(modifier)
        }
    }
}

@Composable
private fun ArticlesLoading(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ArticlesList(
    articles: ImmutableList<Article>,
    onArticleClick: (Article) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier) {
        itemsIndexed(articles, key = { _, item -> item.id }) { index, article ->
            ArticleItem(
                article = article,
                onArticleClick = onArticleClick,
                modifier = Modifier.fillMaxWidth(),
            )
            if (index != articles.lastIndex) {
                HorizontalDivider()
            }
        }
    }
}

@Composable
private fun ArticleItem(
    article: Article,
    onArticleClick: (Article) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.clickable { onArticleClick(article) },
    ) {
        Text(
            text = article.title.orElseUnknown(),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(8.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
        ) {
            Text(
                text = article.author.orElseUnknown(),
                style = MaterialTheme.typography.labelMedium,
            )
            Text(
                text = article.publishedAt.toString(), // This should be formatted properly
                style = MaterialTheme.typography.labelMedium,
            )
        }
        val imageUrl = article.urlToImage
        if (imageUrl != null) {
            Image(
                painter = rememberImagePainter(imageUrl),
                contentDescription = article.title,
                modifier = Modifier.fillMaxWidth(),
            )
        }
        Text(
            text = article.description.orElseUnknown(),
            maxLines = 5,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 8.dp),
        )
    }
}

@Composable
private fun ArticlesError(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier,
    ) {
        Text(text = stringResource(id = R.string.api_error_message))
    }
}
