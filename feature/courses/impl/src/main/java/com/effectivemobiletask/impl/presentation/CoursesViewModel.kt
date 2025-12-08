package com.effectivemobiletask.impl.presentation

import androidx.lifecycle.viewModelScope
import com.effectivemobiletask.base.BaseViewModel
import com.effectivemobiletask.data.mapper.CoursesMapper
import com.effectivemobiletask.domain.repository.CoursesRepository
import com.effectivemobiletask.impl.model.CoursesState
import com.effectivemobiletask.impl.ui.CourseUi
import com.effectivemobiletask.impl.ui.toUiModel
import com.effectivemobiletask.navigation.NavigationProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.filter

@HiltViewModel
class CoursesViewModel @Inject constructor(
    private val repository: CoursesRepository,
    navigationProvider: NavigationProvider
) : BaseViewModel<CoursesState, CoursesEvent>(navigationProvider) {

    init {
        setState(CoursesState(isLoading = true))
        loadCourses()
    }

    fun loadCourses() {
        viewModelScope.launch {
            try {
                val domainCourses = repository.getCourses()
                val uiCourses = domainCourses.map { it.toUiModel() }
                updateState { currentState ->
                    val filtered = applySearchAndSort(
                        uiCourses,
                        currentState.searchQuery,
                        currentState.isSortedByDate
                    )
                    currentState.copy(
                        domainCourses = domainCourses, // Сохраняем доменные модели
                        courses = uiCourses,
                        filteredCourses = filtered,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                emitEvent(CoursesEvent.ShowError("Не удалось загрузить курсы"))
                updateState { it.copy(isLoading = false) }
            }
        }
    }

    fun onCourseClick(courseId: String) {
        val currentState = getCurrentState() ?: return
        val domainCourse = currentState.domainCourses.find { it.id == courseId }

        domainCourse?.let {
            coursesNavigation.navigateToCourseDetails(it)
        } ?: emitEvent(CoursesEvent.ShowError("Курс не найден"))
    }

    fun toggleFavorite(courseId: String) {
        viewModelScope.launch {
            try {
                val currentState = getCurrentState() ?: return@launch
                val course = currentState.courses.find { it.id == courseId }
                course?.let {
                    repository.toggleFavorite(courseId, !it.isFavorite)

                    updateState { state ->
                        val updatedCourses = state.courses.map { course ->
                            if (course.id == courseId) course.copy(isFavorite = !course.isFavorite) else course
                        }
                        val filtered = applySearchAndSort(
                            updatedCourses,
                            state.searchQuery,
                            state.isSortedByDate
                        )
                        state.copy(
                            courses = updatedCourses,
                            filteredCourses = filtered
                        )
                    }
                }
            } catch (e: Exception) {
                emitEvent(CoursesEvent.ShowError("Не удалось обновить избранное"))
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        updateState { currentState ->
            val filtered = applySearchAndSort(
                currentState.courses,
                query,
                currentState.isSortedByDate
            )
            currentState.copy(
                searchQuery = query,
                filteredCourses = filtered
            )
        }
    }

    fun toggleSort() {
        updateState { currentState ->
            val newSortState = !currentState.isSortedByDate
            val filtered = applySearchAndSort(
                currentState.courses,
                currentState.searchQuery,
                newSortState
            )
            currentState.copy(
                isSortedByDate = newSortState,
                filteredCourses = filtered
            )
        }
    }

    private fun applySearchAndSort(
        courses: List<CourseUi>,
        query: String,
        sortedByDate: Boolean
    ): List<CourseUi> {
        var result = courses

        if (query.isNotBlank()) {
            result = result.filter {
                it.title.contains(query, ignoreCase = true) ||
                        it.description.contains(query, ignoreCase = true)
            }
        }

        if (sortedByDate) {
            result = result.sortedByDescending { it.publishDate }
        }

        return result
    }
}