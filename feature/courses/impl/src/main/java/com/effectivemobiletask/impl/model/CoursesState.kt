package com.effectivemobiletask.impl.model

import com.effectivemobiletask.domain.model.Course
import com.effectivemobiletask.impl.ui.CourseUi
import kotlinx.serialization.Serializable

@Serializable
data class CoursesState(
    val domainCourses: List<Course> = emptyList(),
    val courses: List<CourseUi> = emptyList(),
    val filteredCourses: List<CourseUi> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val isSortedByDate: Boolean = false
)
