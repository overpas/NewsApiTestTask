package com.example.newsapitesttask.data.network

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

internal class ApiKeyHeaderInterceptor @Inject constructor(
    private val apiKey: String,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("X-Api-Key", apiKey)
            .url(chain.request().url)
            .build()
        return chain.proceed(request)
    }
}
