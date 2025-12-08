package com.effectivemobiletask.domain.repository

import com.effectivemobiletask.domain.model.Course
import kotlinx.coroutines.flow.Flow

interface CoursesRepository {
    suspend fun getCourses(): List<Course>
    fun getFavoriteCourses(): Flow<List<Course>>
    suspend fun toggleFavorite(courseId: String, isFavorite: Boolean)
    suspend fun getCourseById(courseId: String): Course?
}