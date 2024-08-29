package com.example.newsapitesttask.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.newsapitesttask.R

@Composable
fun String?.orElseUnknown(): String = this ?: stringResource(id = R.string.unknown)
