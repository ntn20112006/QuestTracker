package com.example.questtracker.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.questtracker.data.repository.TaskRepository
import com.example.questtracker.data.entity.Task
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel class for managing tasks in the QuestTracker application.
 * This class handles the business logic for task-related operations and maintains the UI state.
 *
 * @property repository The repository instance for accessing task data
 */
class TaskViewModel(
    private val repository: TaskRepository
) : ViewModel() {

    /**
     * StateFlow that emits the current list of tasks.
     * The list is updated whenever the underlying data changes.
     */
    val tasks: StateFlow<List<Task>> =
        repository.tasks
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    /**
     * Toggles the completion status of a task.
     *
     * @param id The ID of the task to toggle
     */
    fun toggle(id: Int) {
        viewModelScope.launch {
            repository.toggle(id)
        }
    }

    /**
     * Adds a new task to the database.
     *
     * @param task The task to add
     */
    fun add(task: Task) {
        viewModelScope.launch {
            repository.add(task)
        }
    }

    /**
     * Deletes a task from the database.
     *
     * @param task The task to delete
     */
    fun delete(task: Task) {
        viewModelScope.launch {
            repository.delete(task)
        }
    }

    /**
     * Updates an existing task in the database.
     *
     * @param task The task to update
     */
    fun update(task: Task) {
        viewModelScope.launch {
            repository.update(task)
        }
    }
}
