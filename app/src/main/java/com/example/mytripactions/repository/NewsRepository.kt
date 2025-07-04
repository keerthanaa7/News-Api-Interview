package com.example.mytripactions.repository

import com.example.mytripactions.UIState
import com.example.mytripactions.api.NewsArticleDto
import com.example.mytripactions.api.NewsResponse
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.Flow
import retrofit2.Call

interface NewsRepository {
    suspend fun getNews(): Flow<UIState<List<NewsArticleDto>>>
}