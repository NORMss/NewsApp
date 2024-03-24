package com.norm.news_data

import com.norm.database.NewsDatabase
import com.norm.news_data.model.Article
import com.norm.newsapi.NewsApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ArticlesRepository(
    private val database: NewsDatabase,
    private val api: NewsApi,
) {
    fun getAll(): Flow<List<Article>> {
        return database.articlesDao
            .getAll()
            .map { articles ->
                articles.map { it.toArticle() }
            }
    }

    suspend fun search(query: String): Flow<Article> {
        api.everything()
        TODO()
    }
}

sealed class RequestResult<E>(private val date: E?) {
    class InProgress<E>(date: E?) : RequestResult<E>(date)
    class Success<E>(date: E?) : RequestResult<E>(date)
    class Error<E>(val date: E?) : RequestResult<E>(date)
}