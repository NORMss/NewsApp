package com.norm.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RawQuery
import com.norm.database.models.ArticlesDBO
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Query("SELECT * FROM articles")
    fun getAll(): Flow<List<ArticlesDBO>>

    @Insert
    suspend fun insert(articles: List<ArticlesDBO>)

    @Delete
    suspend fun remove(articles: List<ArticlesDBO>)

    @Query("DELETE FROM articles")
    suspend fun clean()
}