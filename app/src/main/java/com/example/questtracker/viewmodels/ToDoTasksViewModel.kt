package com.example.questtracker.viewmodels

import androidx.lifecycle.ViewModel
import com.example.questtracker.data.Date
import com.example.questtracker.data.ToDoTask
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ToDoTasksViewModel : ViewModel() {
    private val _tasks = MutableStateFlow(
        listOf(
            ToDoTask(1, "Walk 1 km", "", false, 2, Date(1, 1, 1), false, null),
            ToDoTask(2, "Read 10 pages", "", false, 2, Date(1, 1, 1), true, null),
            ToDoTask(3, "Meditate 5 min", "", false, 2, Date(1, 1, 1), false, null)
        )
    )
    val tasks: StateFlow<List<ToDoTask>> = _tasks.asStateFlow()

    fun toggle(id: Int) {
        _tasks.update { list ->
            list.map { if (it.id == id.toInt()) it.copy(isComplete = !it.isComplete) else it }
        }
    }

    fun add(title: String) {
        val nextId = (_tasks.value.maxOfOrNull { it.id } ?: 0) + 1
        _tasks.update { it + ToDoTask(nextId, title, "", false, 2, Date(1, 1, 1), false, null) }
    }
}