package com.example.mytripactions.repository

import com.example.mytripactions.UIState
import com.example.mytripactions.api.NewsApi
import com.example.mytripactions.api.NewsArticleDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NewsRepositoryImpl(private val newsApi: NewsApi) : NewsRepository {

    override suspend fun getNews(): Flow<UIState<List<NewsArticleDto>>> = flow {
        emit(UIState.Loading)
        try {
            val response = newsApi.getNews()
            if (response.isSuccessful) {
                val body = response.body()
                if (body?.articles.isNullOrEmpty()) {
                    emit(UIState.Success(emptyList()))
                } else {
                    emit(UIState.Success(body!!.articles))
                }
            } else {
                emit(UIState.Error("error fetching articles :${response.code()}"))
            }
        } catch (e: Exception){
            emit(UIState.Error("error fetching articles :${e.message}"))
        }

    }
}