package com.rappi.composenavigation.di

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class RequestInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        val httpUrl = request.url.newBuilder()
            .addQueryParameter(QUERY_NAME_API_KEY, API_KEY).build()

        request = request.newBuilder().url(httpUrl).build()
        return chain.proceed(request)
    }

    companion object {
        const val QUERY_NAME_API_KEY = "api_key"
        const val API_KEY = "f227568aeb087ee74555ef6b02ba63e4"
    }
}