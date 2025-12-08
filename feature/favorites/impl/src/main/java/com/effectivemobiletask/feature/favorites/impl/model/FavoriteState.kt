package com.effectivemobiletask.feature.favorites.impl.model

import com.effectivemobiletask.domain.model.Course
import com.effectivemobiletask.impl.ui.CourseUi
import kotlinx.serialization.Serializable

@Serializable
data class FavoritesState(
    val domainCourses: List<Course> = emptyList(),
    val favorites: List<CourseUi> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val isSortedByDate: Boolean = false
)

