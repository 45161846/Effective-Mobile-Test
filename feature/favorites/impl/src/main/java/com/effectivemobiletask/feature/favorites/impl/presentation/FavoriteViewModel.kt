package com.effectivemobiletask.feature.favorites.impl.presentation

import androidx.lifecycle.viewModelScope
import com.effectivemobiletask.base.BaseViewModel
import com.effectivemobiletask.data.mapper.CoursesMapper
import com.effectivemobiletask.domain.model.Course
import com.effectivemobiletask.domain.repository.CoursesRepository
import com.effectivemobiletask.feature.favorites.impl.model.FavoritesEvent
import com.effectivemobiletask.feature.favorites.impl.model.FavoritesState
import com.effectivemobiletask.impl.presentation.CoursesEvent
import com.effectivemobiletask.impl.ui.CourseUi
import com.effectivemobiletask.impl.ui.toUiModel
import com.effectivemobiletask.navigation.NavigationProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: CoursesRepository,
    navigationProvider: NavigationProvider
) : BaseViewModel<FavoritesState, FavoritesEvent>(navigationProvider) {

    init {
        setState(FavoritesState(isLoading = true))
        loadFavorites()
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            try {
                repository.getFavoriteCourses().collect { favorites ->
                    val domainToUi = favorites.map { it.toUiModel() }
                    updateState { currentState ->
                        currentState.copy(
                            domainCourses = favorites,
                            favorites = domainToUi,
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                emitEvent(FavoritesEvent.ShowError("Не удалось загрузить избранные курсы"))
                updateState { it.copy(isLoading = false) }
            }
        }
    }

    fun removeFromFavorites(courseId: String) {
        viewModelScope.launch {
            try {
                repository.toggleFavorite(courseId, false)
            } catch (e: Exception) {
                emitEvent(FavoritesEvent.ShowError("Не удалось удалить из избранного"))
            }
        }
    }

    fun onCourseClick(courseId: String) {
        val currentState = getCurrentState() ?: return
        val domainCourse = currentState.domainCourses.find { it.id == courseId }

        domainCourse?.let {
            favoritesNavigation.navigateToCourseDetails(it)
        } ?: emitEvent(FavoritesEvent.ShowError("Курс не найден"))
    }
}