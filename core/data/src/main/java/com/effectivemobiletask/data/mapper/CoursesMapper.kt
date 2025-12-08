package com.effectivemobiletask.data.mapper

import com.effectivemobiletask.data.database.FavoriteCourseEntity
import com.effectivemobiletask.data.network.dto.CourseDto
import com.effectivemobiletask.domain.model.Course

class CoursesMapper {

    fun mapEntityToDomain(entity: FavoriteCourseEntity): Course {
        return Course(
            id = entity.id,
            title = entity.title,
            description = entity.description,
            price = entity.price,
            rating = entity.rating,
            startDate = entity.startDate,
            publishDate = entity.publishDate,
            isFavorite = entity.isFavorite
        )
    }

    fun mapDomainToEntity(domain: Course): FavoriteCourseEntity {
        return FavoriteCourseEntity(
            id = domain.id,
            title = domain.title,
            description = domain.description,
            price = domain.price,
            rating = domain.rating,
            startDate = domain.startDate,
            publishDate = domain.publishDate,
            isFavorite = domain.isFavorite
        )
    }

    fun mapDtoToDomain(dto: CourseDto): Course {
        return Course(
            id = dto.id,
            title = dto.title,
            description = dto.text,
            price = dto.price,
            rating = dto.rate,
            startDate = dto.startDate,
            publishDate = dto.publishDate,
            isFavorite = dto.hasLike
        )
    }
}