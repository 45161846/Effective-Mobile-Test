package com.effectivemobiletask.details.impl

import com.effectivemobiletask.base.BaseViewModel
import com.effectivemobiletask.navigation.NavigationProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CourseDetailsViewModel @Inject constructor(
    private val navigationProvider: NavigationProvider
): BaseViewModel<CourseDetails, CourseDetailsEvent>(navigationProvider) {



}