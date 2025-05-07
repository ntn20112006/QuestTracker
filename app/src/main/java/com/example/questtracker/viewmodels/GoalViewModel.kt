package com.example.questtracker.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.questtracker.data.repository.GoalRepository
import com.example.questtracker.data.entity.Goal
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel class for managing goals in the QuestTracker application.
 * This class handles the business logic for goal-related operations and maintains the UI state.
 *
 * @property repository The repository instance for accessing goal data
 */
class GoalViewModel(
    private val repository: GoalRepository
) : ViewModel() {

    /**
     * StateFlow that emits the current list of goals.
     * The list is updated whenever the underlying data changes.
     */
    val goals: StateFlow<List<Goal>> =
        repository.goals
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    /**
     * Toggles the completion status of a goal.
     *
     * @param id The ID of the goal to toggle
     */
    fun toggle(id: Int) {
        viewModelScope.launch {
            repository.toggle(id)
        }
    }

    /**
     * Adds a new goal to the database.
     *
     * @param goal The goal to add
     */
    fun add(goal: Goal) {
        viewModelScope.launch {
            repository.add(goal)
        }
    }

    /**
     * Deletes a goal from the database.
     *
     * @param goal The goal to delete
     */
    fun delete(goal: Goal) {
        viewModelScope.launch {
            repository.delete(goal)
        }
    }

    /**
     * Updates an existing goal in the database.
     *
     * @param goal The goal to update
     */
    fun update(goal: Goal) {
        viewModelScope.launch {
            repository.update(goal)
        }
    }
}
