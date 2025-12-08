package com.effectivemobiletask.impl.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.effectivemobiletask.domain.model.Course
import com.effectivemobiletask.feature.courses.impl.R
import com.effectivemobiletask.feature.courses.impl.databinding.ItemCourseBinding

class CoursesAdapter(
    private val onFavoriteClick: (String) -> Unit,
    private val onDetailsClick: (String) -> Unit
) : ListAdapter<CourseUi, CoursesAdapter.CourseViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding = ItemCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CourseViewHolder(
        private val binding: ItemCourseBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(course: CourseUi) {
            with(binding) {
                ratingText.text = course.rating.toString()
                dateText.text = course.formattedDate
                titleText.text = course.title
                descriptionText.text = course.description
                priceText.text = course.price

                val favoriteIcon = if (course.isFavorite) {
                    R.drawable.ic_favorite_filled
                } else {
                    R.drawable.ic_favorite_border
                }
                favoriteButton.setImageResource(favoriteIcon)

                favoriteButton.setOnClickListener {
                    onFavoriteClick(course.id)
                }

                detailsButton.setOnClickListener {
                    onDetailsClick(course.id)
                }
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<CourseUi>() {
        override fun areItemsTheSame(oldItem: CourseUi, newItem: CourseUi): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CourseUi, newItem: CourseUi): Boolean {
            return oldItem == newItem
        }
    }
}