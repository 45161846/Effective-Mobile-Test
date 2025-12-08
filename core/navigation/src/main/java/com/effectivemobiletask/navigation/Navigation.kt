package com.effectivemobiletask.navigation

import com.effectivemobiletask.domain.model.Course

interface AuthNavigation {
    fun navigateToMainTabs()
}

interface CoursesNavigation {
    fun navigateToCourseDetails(course: Course)
}

interface FavoritesNavigation {
    fun navigateToCourseDetails(course: Course)
}

interface NavigationProvider {
    fun getAuthNavigation(): AuthNavigation
    fun getCoursesNavigation(): CoursesNavigation
    fun getFavoritesNavigation(): FavoritesNavigation
}