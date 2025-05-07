package com.example.questtracker.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.questtracker.data.repository.ToDoTaskRepository

/**
 * Factory class for creating ToDoTasksViewModel instances.
 * This class is required because ToDoTasksViewModel has a non-empty constructor.
 *
 * @property repository The repository instance to be passed to the ViewModel
 */
class ToDoTasksViewModelFactory(
    private val repository: ToDoTaskRepository
) : ViewModelProvider.Factory {
    /**
     * Creates a new instance of the specified ViewModel class.
     *
     * @param modelClass The class of the ViewModel to create
     * @return A new instance of ToDoTasksViewModel
     * @throws IllegalArgumentException if the modelClass is not ToDoTasksViewModel
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ToDoTasksViewModel::class.java)) {
            return ToDoTasksViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
