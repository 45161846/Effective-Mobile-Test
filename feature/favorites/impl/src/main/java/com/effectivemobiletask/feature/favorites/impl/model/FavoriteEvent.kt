package com.effectivemobiletask.feature.favorites.impl.model

sealed interface FavoritesEvent {
    data class ShowError(val message: String) : FavoritesEvent
    data class NavigateToCourseDetails(val courseId: String) : FavoritesEvent
    data class RemoveFromFavorite(val courseId: String) : FavoritesEvent
    object ToggleSort : FavoritesEvent
}