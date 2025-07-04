package com.example.mytripactions

sealed class UIState<out T> {
    object Loading : UIState<Nothing>()
    data class Success<T>(val data: T) : UIState<T>()
    data class Error<T>(val message :String) : UIState<T>()
}
