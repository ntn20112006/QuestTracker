package com.example.questtracker.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.questtracker.data.ToDoTaskRepository

class ToDoTasksViewModelFactory(
    private val repository: ToDoTaskRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ToDoTasksViewModel(repository) as T
    }
}
