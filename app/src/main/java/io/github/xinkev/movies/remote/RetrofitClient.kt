package io.github.xinkev.movies.remote

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.xinkev.movies.BuildConfig
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@OptIn(ExperimentalSerializationApi::class)
@Singleton
class RetrofitClient @Inject constructor(@ApplicationContext context: Context) {
    private val contentType = "application/json".toMediaType()
    private val clientBuilder = OkHttpClient.Builder()
        .addInterceptor(
            ChuckerInterceptor.Builder(context)
                .collector(ChuckerCollector(context))
                .maxContentLength(250000L)
                .redactHeaders(emptySet())
                .alwaysReadResponseBody(false)
                .build()
        )
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofitBuilder = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory(contentType = contentType))

    val movieDBApi: MovieDBApi

    init {
        movieDBApi = createMovieDBApi()
    }

    private fun createMovieDBApi(): MovieDBApi {
        val client = clientBuilder
            .addInterceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                requestBuilder
                    .addHeader("Content-Type", "application/json;charset=utf-8")
                    .addHeader("Authorization", "Bearer ${BuildConfig.API_KEY}")
                    .build()
                chain.proceed(requestBuilder.build())
            }.build()
        val retrofit = retrofitBuilder
            .baseUrl("https://api.themoviedb.org/3/")
            .client(client)
            .build()
        return retrofit.create(MovieDBApi::class.java)
    }
}