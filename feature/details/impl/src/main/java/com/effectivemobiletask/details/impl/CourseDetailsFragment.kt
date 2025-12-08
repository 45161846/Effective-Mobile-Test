package com.effectivemobiletask.details.impl

import android.view.View
import androidx.fragment.app.viewModels
import com.effectivemobiletask.base.BaseFragment
import com.effectivemobiletask.details.impl.databinding.CourseDetailsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CourseDetailsFragment : BaseFragment<
        CourseDetails,
        CourseDetailsEvent,
        CourseDetailsViewModel,
        CourseDetailsFragmentBinding>()
{

    override val viewModel: CourseDetailsViewModel by viewModels()

    override fun getLayoutRes(): Int = R.layout.course_details_fragment

    override fun createBinding(view: View): CourseDetailsFragmentBinding =
        CourseDetailsFragmentBinding.bind(view)

    override fun setupUI() {

    }

    override fun renderState(state: CourseDetails) {

    }


    override fun handleEvent(event: CourseDetailsEvent) {

    }

}