package com.example.recipe_mate


sealed class NetworkResponse<out T> {
    data class Success<out T>(val data: T) : NetworkResponse<T>()
    data class Error(val exception: Throwable): NetworkResponse<Nothing>()
    object Loading: NetworkResponse<Nothing>()

}