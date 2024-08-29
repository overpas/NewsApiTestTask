package com.example.newsapitesttask.di.data

import com.example.newsapitesttask.data.ArticlesRepositoryImpl
import com.example.newsapitesttask.domain.ArticlesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataModule {

    @Binds
    @Singleton
    abstract fun articlesRepository(impl: ArticlesRepositoryImpl): ArticlesRepository
}
