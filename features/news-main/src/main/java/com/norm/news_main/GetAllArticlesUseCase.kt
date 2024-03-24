package com.norm.news_main

import com.norm.news_data.ArticlesRepository
import com.norm.news_data.model.Article
import kotlinx.coroutines.flow.Flow

class GetAllArticlesUseCase(
    private val repository: ArticlesRepository
) {

    operator fun invoke(): Flow<List<Article>> {
        return repository.getAll()
    }
}