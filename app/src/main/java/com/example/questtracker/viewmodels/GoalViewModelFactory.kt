package com.example.questtracker.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.questtracker.data.repository.GoalRepository

/**
 * Factory class for creating GoalViewModel instances.
 * This class is required because GoalViewModel has a non-empty constructor.
 *
 * @property repository The repository instance to be passed to the ViewModel
 */
class GoalViewModelFactory(
    private val repository: GoalRepository
) : ViewModelProvider.Factory {
    /**
     * Creates a new instance of the specified ViewModel class.
     *
     * @param modelClass The class of the ViewModel to create
     * @return A new instance of GoalViewModel
     * @throws IllegalArgumentException if the modelClass is not GoalViewModel
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GoalViewModel::class.java)) {
            return GoalViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
