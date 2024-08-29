package com.example.newsapitesttask.di.data.network

import com.example.newsapitesttask.BuildConfig
import com.example.newsapitesttask.data.network.ApiKeyHeaderInterceptor
import com.example.newsapitesttask.data.network.NewsApi
import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import kotlinx.serialization.StringFormat
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    @Singleton
    @IntoSet
    fun apiKeyHeaderInterceptor(): Interceptor =
        ApiKeyHeaderInterceptor(BuildConfig.NEWS_API_KEY)

    @Provides
    @Singleton
    @IntoSet
    fun loggingInterceptor(): Interceptor =
        HttpLoggingInterceptor()

    @Provides
    @Singleton
    fun okHttpClient(
        interceptors: Set<@JvmSuppressWildcards Interceptor>,
    ): OkHttpClient =
        OkHttpClient.Builder()
            .apply {
                interceptors.forEach(::addInterceptor)
            }
            .connectTimeout(@Suppress("MagicNumber") 30, TimeUnit.SECONDS)
            .build()

    @Provides
    @Singleton
    fun jsonFormat(): StringFormat =
        Json {
            prettyPrint = true
            ignoreUnknownKeys = true
        }

    @Provides
    @Singleton
    fun jsonMediaType(): MediaType =
        "application/json; charset=UTF8".toMediaType()

    @Provides
    @Singleton
    @IntoSet
    fun jsonConverterFactory(
        jsonFormat: StringFormat,
        jsonMediaType: MediaType,
    ): Converter.Factory =
        jsonFormat.asConverterFactory(jsonMediaType)

    @Provides
    @Singleton
    @IntoSet
    fun resultCallAdapterFactory(): CallAdapter.Factory =
        ResultCallAdapterFactory.create()

    @Provides
    @Singleton
    fun retrofit(
        okHttpClient: OkHttpClient,
        converterFactories: Set<@JvmSuppressWildcards Converter.Factory>,
        callAdapterFactories: Set<@JvmSuppressWildcards CallAdapter.Factory>,
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.NEWS_API_BASE_URL)
            .client(okHttpClient)
            .apply {
                converterFactories.forEach(::addConverterFactory)
            }
            .apply {
                callAdapterFactories.forEach(::addCallAdapterFactory)
            }
            .build()

    @Provides
    @Singleton
    fun newsApi(retrofit: Retrofit): NewsApi =
        retrofit.create()
}
