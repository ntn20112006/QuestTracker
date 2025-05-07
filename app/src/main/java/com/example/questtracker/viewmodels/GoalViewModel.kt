package com.example.questtracker.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.questtracker.data.entity.Goal
import com.example.questtracker.data.repository.GoalRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class GoalViewModel(
    private val repository: GoalRepository
) : ViewModel() {

    // expose the list of tasks
    val goals: StateFlow<List<Goal>> =
        repository.goals
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    // toggle complete/incomplete
    fun toggle(id: Int) {
        viewModelScope.launch {
            repository.toggle(id)
        }
    }

    // add a full ToDoTask
    fun add(goal: Goal) {
        viewModelScope.launch {
            repository.add(goal)
        }
    }

    // (optional) delete a task
    fun delete(goal: Goal) {
        viewModelScope.launch {
            repository.delete(goal)
        }
    }

    // (optional) update a task
    fun update(goal: Goal) {
        viewModelScope.launch {
            repository.update(goal)
        }
    }
}
