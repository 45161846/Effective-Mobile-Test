package com.effectivemobiletask.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Course(
    val id: String,
    val title: String,
    val description: String,
    val price: String,
    val rating: Double,
    val startDate: String,
    val publishDate: String,
    val isFavorite: Boolean
) : java.io.Serializable