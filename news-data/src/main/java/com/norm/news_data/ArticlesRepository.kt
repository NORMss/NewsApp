package com.norm.news_data

import com.norm.database.NewsDatabase
import com.norm.database.models.ArticleDBO
import com.norm.news_data.model.Article
import com.norm.newsapi.NewsApi
import com.norm.newsapi.models.ArticleDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class ArticlesRepository(
    private val database: NewsDatabase,
    private val api: NewsApi,
) {
    fun getAll(): Flow<RequestResult<*>> {

        val cachedAllArticles: Flow<RequestResult.Success<List<ArticleDBO>>> =
            getAllFromDatabase()

        val remoteArticles = getAllFromServer()

        cachedAllArticles.map {

        }
//        return cachedAllArticles.combine(remoteArticles)
        TODO()
    }

    private fun getAllFromServer(): Flow<RequestResult<*>> {
        return flow {
            emit(api.everything())
        }.map { result ->
            result.toRequestResult()
        }.onEach { requestResult ->
            if (requestResult is RequestResult.Success) {
                saveResponseToCache(checkNotNull(requestResult.date).articles)
            }
        }
    }

    private suspend fun saveResponseToCache(date: List<ArticleDTO>) {
        val dbos = date.map { articleDto ->
            articleDto.toArticleDbo()
        }
        database.articlesDao.insert(dbos)
    }

    private fun getAllFromDatabase(): Flow<RequestResult.Success<List<ArticleDBO>>> {
        return database.articlesDao.getAll().map {
            RequestResult.Success(it)
        }
    }

    suspend fun search(query: String): Flow<Article> {
        api.everything()
        TODO()
    }
}

sealed class RequestResult<E>(internal val date: E? = null) {
    class InProgress<E>(date: E) : RequestResult<E>(date)
    class Success<E>(date: E) : RequestResult<E>(date)
    class Error<E>() : RequestResult<E>()
}

internal fun <T : Any> RequestResult<T?>.requireData(): T = checkNotNull(date)

internal fun <I, O> RequestResult<I>.map(mapper: (I?) -> O): RequestResult<O> {
    val outData = mapper(date)
    return when (this) {
        is RequestResult.Success -> RequestResult.Success(outData)
        is RequestResult.Error -> RequestResult.Error()
        is RequestResult.InProgress -> RequestResult.InProgress(outData)
    }
}

internal fun <T> Result<T>.toRequestResult(): RequestResult<T> {
    return when {
        isSuccess -> RequestResult.Success(getOrThrow())
        isFailure -> RequestResult.Error()
        else -> error("Impossible branch")
    }
}