package com.effectivemobiletask.feature.favorites.impl.ui

import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.effectivemobiletask.base.BaseFragment
import com.effectivemobiletask.feature.favorites.impl.R
import com.effectivemobiletask.feature.favorites.impl.databinding.FragmentFavoritesBinding
import com.effectivemobiletask.feature.favorites.impl.model.FavoritesEvent
import com.effectivemobiletask.feature.favorites.impl.model.FavoritesState
import com.effectivemobiletask.feature.favorites.impl.presentation.FavoritesViewModel
import com.effectivemobiletask.impl.ui.CoursesAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : BaseFragment<FavoritesState, FavoritesEvent, FavoritesViewModel, FragmentFavoritesBinding>() {

    override val viewModel: FavoritesViewModel by viewModels()

    private lateinit var adapter: CoursesAdapter

    private lateinit var progressBar: ProgressBar
    private lateinit var coursesContainer: LinearLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyState: TextView

    override fun getLayoutRes(): Int = R.layout.fragment_favorites

    override fun createBinding(view: View): FragmentFavoritesBinding =
        FragmentFavoritesBinding.bind(view)

    override fun setupUI() {
        // Инициализация View
        progressBar = binding.progressBar
        coursesContainer = binding.coursesContainer
        recyclerView = binding.recyclerView
        emptyState = binding.emptyState

        // Настройка адаптера
        adapter = CoursesAdapter(
            onFavoriteClick = { courseId ->
                viewModel.removeFromFavorites(courseId)
            },
            onDetailsClick = { course ->
                viewModel.onCourseClick(course)
            }
        )

        recyclerView.adapter = adapter
    }

    override fun renderState(state: FavoritesState) {
        progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        coursesContainer.visibility = if (state.isLoading) View.GONE else View.VISIBLE

        adapter.submitList(state.favorites)

        emptyState.visibility = if (state.favorites.isEmpty()) View.VISIBLE else View.GONE
    }

    override fun handleEvent(event: FavoritesEvent) {
        when (event) {
            is FavoritesEvent.NavigateToCourseDetails -> {

//                findNavController().navigate(
//                    FavoritesFragmentDirections.actionFavoritesToCourseDetails(event.courseId)
//                )
            }
            is FavoritesEvent.ShowError -> {
                Snackbar.make(binding.root, event.message, Snackbar.LENGTH_LONG).show()
            }

            else -> {}
        }
    }

    override fun handleLoading(isLoading: Boolean) {

    }
}