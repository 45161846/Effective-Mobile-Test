package com.effectivemobiletask.feature.cources.api.repository

import com.effectivemobiletask.domain.model.Course
import kotlinx.coroutines.flow.Flow

interface CoursesRepository {
    suspend fun getCourses(): Flow<Result<List<Course>>>
    suspend fun toggleFavorite(courseId: String)
    suspend fun getFavoriteCourses(): Flow<List<Course>>
}