package com.example.newsapitesttask.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    @InitialDelay
    fun initialViewModelDelay(): Long = InitialDelay.DEFAULT
}

/**
 * For testing purposes. Equals 0L in production code.
 */
@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class InitialDelay {

    companion object {

        const val DEFAULT = 0L
    }
}
