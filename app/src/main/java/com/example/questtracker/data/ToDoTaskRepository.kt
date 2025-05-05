package com.example.questtracker.data

import com.example.questtracker.data.DAO.ToDoTaskDao
import com.example.questtracker.data.entity.ToDoTask
import kotlinx.coroutines.flow.Flow

class ToDoTaskRepository(private val dao: ToDoTaskDao) {
    val tasks: Flow<List<ToDoTask>> = dao.getAll()

    suspend fun add(task: ToDoTask) = dao.insert(task)

    suspend fun delete(task: ToDoTask) = dao.delete(task)

    suspend fun update(task: ToDoTask) = dao.update(task)

    suspend fun toggle(id: Int) {
        val task = dao.getById(id) ?: return
        dao.update(task.copy(isComplete = !task.isComplete))
    }
}
