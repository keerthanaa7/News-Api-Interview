package com.example.mytripactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytripactions.api.NewsArticleDto
import com.example.mytripactions.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsRepository: NewsRepository) : ViewModel() {

    private val articlesflow = MutableStateFlow<UIState<List<NewsArticleDto>>>(UIState.Loading)
    val articles: StateFlow<UIState<List<NewsArticleDto>>> = articlesflow

    fun getArticlesList(){
        viewModelScope.launch {
            newsRepository.getNews().collect{
                articlesflow.value = it
            }
        }
    }

}