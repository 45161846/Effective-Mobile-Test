package com.effectivemobiletask.data.repository

import com.effectivemobiletask.data.database.FavoritesDao
import com.effectivemobiletask.data.mapper.CoursesMapper
import com.effectivemobiletask.data.network.ApiService
import com.effectivemobiletask.domain.model.Course
import com.effectivemobiletask.domain.repository.CoursesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CoursesRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val favoritesDao: FavoritesDao,
    private val mapper: CoursesMapper
) : CoursesRepository {

    override suspend fun getCourses(): List<Course> {
        return try {
            val response = apiService.getCourses()
            if (response.isSuccessful) {
                val coursesResponse = response.body()
                val networkCourses = coursesResponse?.courses?.map { mapper.mapDtoToDomain(it) } ?: emptyList()

                // Синхронизируем с локальными избранными
                syncFavoritesWithNetwork(networkCourses)

                // Возвращаем курсы с актуальным состоянием избранного
                getCoursesWithFavorites(networkCourses)
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override fun getFavoriteCourses(): Flow<List<Course>> {
        return favoritesDao.getFavoriteCourses().map { entities ->
            entities.map { mapper.mapEntityToDomain(it) }
        }
    }

    override suspend fun toggleFavorite(courseId: String, isFavorite: Boolean) {
        if (isFavorite) {
            val course = getCourseById(courseId)
            course?.let {
                favoritesDao.insertOrUpdate(mapper.mapDomainToEntity(it.copy(isFavorite = true)))
            }
        } else {
            favoritesDao.removeFromFavorites(courseId)
        }
    }

    override suspend fun getCourseById(courseId: String): Course? {
        // Сначала ищем в локальной базе избранных
        val localFavorite = favoritesDao.getFavoriteById(courseId)
        if (localFavorite != null) {
            return mapper.mapEntityToDomain(localFavorite)
        }

        // Если нет в избранных, ищем в сетевых данных
        val response = apiService.getCourses()
        if (response.isSuccessful) {
            val coursesResponse = response.body()
            return coursesResponse?.courses?.find { it.id == courseId }?.let { mapper.mapDtoToDomain(it) }
        }

        return null
    }

    private suspend fun getCoursesWithFavorites(networkCourses: List<Course>): List<Course> {
        val favoriteIds = favoritesDao.getFavoriteCourses().first().map { it.id }.toSet()
        return networkCourses.map { course ->
            course.copy(isFavorite = course.id in favoriteIds)
        }
    }

    private suspend fun syncFavoritesWithNetwork(networkCourses: List<Course>) {
        val localFavorites = favoritesDao.getFavoriteCourses().first()
        val localFavoriteIds = localFavorites.map { it.id }.toSet()

        // Обновляем локальные избранные на основе сетевых данных
        networkCourses
            .filter { it.isFavorite && it.id !in localFavoriteIds }
            .forEach { course ->
                favoritesDao.insertOrUpdate(mapper.mapDomainToEntity(course))
            }
    }
}