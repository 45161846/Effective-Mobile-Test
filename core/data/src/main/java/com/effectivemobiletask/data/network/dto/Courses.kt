package com.effectivemobiletask.data.network.dto

data class CoursesResponse(
    val courses: List<CourseDto>
)

data class CourseDto(
    val id: String,
    val title: String,
    val text: String,
    val price: String,
    val rate: Double,
    val startDate: String,
    val publishDate: String,
    val hasLike: Boolean
)