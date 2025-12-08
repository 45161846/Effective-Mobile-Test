package com.effectivemobiletask.impl.presentation

sealed interface CoursesEvent {
    data class ShowError(val message: String) : CoursesEvent
    data class NavigateToCourseDetails(val courseId: String) : CoursesEvent
    data class ToggleFavorite(val courseId: String) : CoursesEvent
    object ToggleSort : CoursesEvent
}