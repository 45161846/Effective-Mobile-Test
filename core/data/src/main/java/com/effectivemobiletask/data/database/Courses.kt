package com.effectivemobiletask.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_courses")
data class FavoriteCourseEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val price: String,
    val rating: Double,
    val startDate: String,
    val publishDate: String,
    val isFavorite: Boolean,
    val lastUpdated: Long = System.currentTimeMillis()
)