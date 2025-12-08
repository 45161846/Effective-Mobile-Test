package com.effectivemobiletask.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(course: FavoriteCourseEntity)

    @Query("SELECT * FROM favorite_courses")
    fun getFavoriteCourses(): Flow<List<FavoriteCourseEntity>>

    @Query("SELECT * FROM favorite_courses WHERE id = :courseId")
    suspend fun getFavoriteById(courseId: String): FavoriteCourseEntity?

    @Query("DELETE FROM favorite_courses WHERE id = :courseId")
    suspend fun removeFromFavorites(courseId: String)

    @Query("SELECT COUNT(*) FROM favorite_courses WHERE id = :courseId")
    suspend fun isCourseFavorite(courseId: String): Int
}