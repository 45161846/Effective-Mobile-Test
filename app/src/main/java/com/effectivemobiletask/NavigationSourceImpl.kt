package com.effectivemobiletask

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.effectivemobiletask.domain.model.Course
import com.effectivemobiletask.navigation.AuthNavigation
import com.effectivemobiletask.navigation.CoursesNavigation
import com.effectivemobiletask.navigation.FavoritesNavigation
import com.effectivemobiletask.navigation.NavigationProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigationSourceImpl @Inject constructor(
    private val application: Application
) : NavigationProvider, AuthNavigation, CoursesNavigation, FavoritesNavigation {

    override fun getAuthNavigation(): AuthNavigation = this
    override fun getCoursesNavigation(): CoursesNavigation = this
    override fun getFavoritesNavigation(): FavoritesNavigation = this

    override fun navigateToMainTabs() {
        findNavController().navigate(R.id.action_global_main_tabs)
    }

    override fun navigateToCourseDetails(course: Course) {
        val bundle = Bundle().apply {
            putSerializable("course", course)
        }
        findNavController().navigate(R.id.action_global_course_details, bundle)
    }

    private fun findNavController(): NavController {
        val activity = (application as? MyApp)?.currentActivity
            ?: throw IllegalStateException("No current activity")
        return Navigation.findNavController(activity, R.id.nav_host_fragment)
    }
}