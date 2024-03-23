package com.norm.newsapi

import androidx.annotation.IntRange
import com.norm.newsapi.models.Articles
import com.norm.newsapi.models.Language
import com.norm.newsapi.models.SortBy
import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Date

/*
* [API Documentations] (https://newsapi.org/docs/)
* */
interface NewsApi {
    /**
    * API details [here](https://newsapi.org/docs/endpoints/everything)
    * */
    @GET("/everything")
    suspend fun everything(
        @Query("q") query: String? = null,
        @Query("from") from: Date? = null,
        @Query("to") to: Date? = null,
        @Query("language") language: List<Language>? = null,
        @Query("sortBy") sortBy: SortBy? = null,
        @Query("pageSize") @IntRange(from = 0, to = 100) pageSize: Int = 100,
        @Query("page") @IntRange(from = 1) page: Int = 1,
    ): Response<Articles>
}

fun newsApi(
    baseUrl: String,
    okHttpClient: OkHttpClient? = null,
    json: Json = Json,
): NewsApi {
    val retrofit = retrofit(baseUrl, okHttpClient, json)
    return retrofit.create(NewsApi::class.java)
}

private fun retrofit(
    baseUrl: String,
    okHttpClient: OkHttpClient?,
    json: Json,
): Retrofit {
    val jsonConverterFactory =
        json.asConverterFactory(MediaType.get("application/json; charset=UTF8"))
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(jsonConverterFactory)
        .addCallAdapterFactory(ResultCallAdapterFactory.create())
        .run {
            if (okHttpClient != null)
                client(okHttpClient)
            else
                this
        }
        .build()
}