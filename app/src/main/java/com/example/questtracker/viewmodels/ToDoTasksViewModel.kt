package com.example.questtracker.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.questtracker.data.ToDoTaskRepository
import com.example.questtracker.data.entity.ToDoTask
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ToDoTasksViewModel(
    private val repository: ToDoTaskRepository
) : ViewModel() {

    // expose the list of tasks
    val tasks: StateFlow<List<ToDoTask>> =
        repository.tasks
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    // toggle complete/incomplete
    fun toggle(id: Int) {
        viewModelScope.launch {
            repository.toggle(id)
        }
    }

    // add a full ToDoTask
    fun add(task: ToDoTask) {
        viewModelScope.launch {
            repository.add(task)
        }
    }

    // (optional) delete a task
    fun delete(task: ToDoTask) {
        viewModelScope.launch {
            repository.delete(task)
        }
    }

    // (optional) update a task
    fun update(task: ToDoTask) {
        viewModelScope.launch {
            repository.update(task)
        }
    }
}
