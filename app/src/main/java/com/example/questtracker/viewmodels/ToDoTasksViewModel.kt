package com.example.questtracker.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.questtracker.data.Date
import com.example.questtracker.data.ToDoTaskRepository
import com.example.questtracker.data.entity.ToDoTask
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ToDoTasksViewModel(
    private val repository: ToDoTaskRepository
) : ViewModel() {
    val tasks: StateFlow<List<ToDoTask>> =
        repository.tasks.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun toggle(id: Int) {
        viewModelScope.launch { repository.toggle(id) }
    }

    fun add(title: String) {
        viewModelScope.launch {
            repository.add(
                ToDoTask(
                    title = title,
                    description = "",
                    isRecurring = false,
                    recurrenceInterval = 2,
                    deadline = Date(1, 1, 1),
                    isComplete = false,
                )
            )
        }
    }
}
