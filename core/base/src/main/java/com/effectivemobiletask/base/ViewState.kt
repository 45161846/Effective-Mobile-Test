package com.effectivemobiletask.base

sealed interface ViewState<out T> {
    object Loading : ViewState<Nothing>
    data class Success<T>(val data: T) : ViewState<T>
    data class Error(val message: String, val retryAction: (() -> Unit)? = null) : ViewState<Nothing>
}