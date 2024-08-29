package com.example.newsapitesttask.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ArticleDetailsScreen(
    viewModel: ArticleDetailsViewModel,
    onNavigateUp: () -> Unit,
    onReadMoreClick: (Article) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateUp,
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back),
                        )
                    }
                },
                scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
            )
        },
        modifier = modifier,
    ) {
        Box(modifier = Modifier.padding(it)) {
            val theState by viewModel.state.collectAsStateWithLifecycle()
            when (val state = theState) {
                is ArticleDetailsState.Initial -> Unit
                is ArticleDetailsState.Details -> {
                    ArticleDetailsContent(
                        article = state.article,
                        onReadMoreClick = onReadMoreClick,
                    )
                }
            }
        }
    }
}

@Composable
private fun ArticleDetailsContent(
    article: Article,
    onReadMoreClick: (Article) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier.verticalScroll(scrollState),
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
            text = article.content.orElseUnknown(),
            maxLines = 5,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 8.dp),
        )
        TextButton(
            onClick = { onReadMoreClick(article) },
            modifier = Modifier.align(Alignment.CenterHorizontally),
        ) {
            Text(text = stringResource(id = R.string.article_more))
        }
    }
}
