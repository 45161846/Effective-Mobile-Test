package com.effectivemobiletask.impl.ui

import com.effectivemobiletask.domain.model.Course
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.Locale

@Serializable
data class CourseUi(
    val id: String,
    val title: String,
    val description: String,
    val price: String,
    val rating: Double,
    val formattedDate: String,
    val publishDate: String,
    val isFavorite: Boolean
)

fun Course.toUiModel(): CourseUi {
    return CourseUi(
        id = id,
        title = title,
        description = description,
        price = formatPrice(price),
        rating = rating,
        formattedDate = formatDate(startDate),
        publishDate = publishDate,
        isFavorite = isFavorite
    )
}

private fun formatDate(dateString: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = inputFormat.parse(dateString) ?: return dateString

        val outputFormat = SimpleDateFormat("d MMMM yyyy", Locale("ru"))
        outputFormat.format(date)
    } catch (e: Exception) {
        dateString
    }
}

private fun formatPrice(price: String): String {
    return "$price ₽"
}