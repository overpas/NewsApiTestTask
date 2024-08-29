package com.example.newsapitesttask.di.data.db

import android.content.Context
import androidx.room.Room
import com.example.newsapitesttask.data.db.NewsDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DbModule {

    @Provides
    @Singleton
    fun newsDb(@ApplicationContext context: Context): NewsDb =
        Room.databaseBuilder(context, NewsDb::class.java, "news.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun articlesDao(newsDb: NewsDb) = newsDb.articlesDao()
}
