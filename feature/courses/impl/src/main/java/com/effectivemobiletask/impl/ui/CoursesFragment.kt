package com.effectivemobiletask.feature.cources.impl.ui

import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.effectivemobiletask.base.BaseFragment
import com.effectivemobiletask.feature.courses.impl.R
import com.effectivemobiletask.feature.courses.impl.databinding.FragmentCoursesBinding
import com.effectivemobiletask.impl.model.CoursesState
import com.effectivemobiletask.impl.presentation.CoursesEvent
import com.effectivemobiletask.impl.presentation.CoursesViewModel
import com.effectivemobiletask.impl.ui.CoursesAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoursesFragment : BaseFragment<CoursesState, CoursesEvent, CoursesViewModel, FragmentCoursesBinding>() {

    override val viewModel: CoursesViewModel by viewModels()
    private lateinit var adapter: CoursesAdapter

    override fun getLayoutRes(): Int = R.layout.fragment_courses

    override fun createBinding(view: View): FragmentCoursesBinding =
        FragmentCoursesBinding.bind(view)

    override fun setupUI() {
        with(binding) {
            coursesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            adapter = CoursesAdapter(
                onFavoriteClick = { courseId ->
                    viewModel.toggleFavorite(courseId)
                },
                onDetailsClick = { courseId ->
                    viewModel.onCourseClick(courseId)
                }
            )
            coursesRecyclerView.adapter = adapter

            sortButton.setOnClickListener {
                viewModel.toggleSort()
            }

            sortText.setOnClickListener {
                viewModel.toggleSort()
            }
        }
    }

    override fun renderState(state: CoursesState) {
        adapter.submitList(state.courses)

        if (state.isLoading) {
            // TODO
        }
    }

    override fun handleEvent(event: CoursesEvent) {
        when (event) {
            is CoursesEvent.ShowError -> {
                Snackbar.make(binding.root, event.message, Snackbar.LENGTH_LONG).show()
            }
            is CoursesEvent.NavigateToCourseDetails -> {
                // Навигация к деталям курса
//                findNavController().navigate(R.id.courseDetailsFragment) TODO
            }
            is CoursesEvent.ToggleFavorite -> {
                Snackbar.make(binding.root, "Избранное обновлено", Snackbar.LENGTH_SHORT).show()
            }
            is CoursesEvent.ToggleSort -> {
                val message = if (viewModel.getCurrentState()?.isSortedByDate == true) {
                    "Сортировка по дате (новые сначала)"
                } else {
                    "Сортировка по дате (старые сначала)"
                }
                Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun handleLoading(isLoading: Boolean) {
        // Реализация индикатора загрузки
        binding.coursesRecyclerView.visibility = if (isLoading) View.GONE else View.VISIBLE
        // progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}