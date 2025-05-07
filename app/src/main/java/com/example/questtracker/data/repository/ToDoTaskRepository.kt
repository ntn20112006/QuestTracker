package com.example.questtracker.data.repository

import com.example.questtracker.data.DAO.ToDoTaskDao
import com.example.questtracker.data.entity.ToDoTask
import kotlinx.coroutines.flow.Flow

/**
 * Repository class for managing tasks in the QuestTracker application.
 * This class serves as a single source of truth for task data and provides
 * a clean API for the ViewModel to interact with the data layer.
 *
 * @property dao The Data Access Object for performing database operations on tasks
 */
class ToDoTaskRepository(private val dao: ToDoTaskDao) {
    /**
     * Flow that emits the current list of tasks.
     * The list is updated whenever the underlying data changes.
     */
    val tasks: Flow<List<ToDoTask>> = dao.getAll()

    /**
     * Adds a new task to the database.
     *
     * @param task The task to add
     */
    suspend fun add(task: ToDoTask) = dao.insert(task)

    /**
     * Deletes a task from the database.
     *
     * @param task The task to delete
     */
    suspend fun delete(task: ToDoTask) = dao.delete(task)

    /**
     * Updates an existing task in the database.
     *
     * @param task The task to update
     */
    suspend fun update(task: ToDoTask) = dao.update(task)

    /**
     * Toggles the completion status of a task.
     * If the task is not found, the operation is silently ignored.
     *
     * @param id The ID of the task to toggle
     */
    suspend fun toggle(id: Int) {
        val task = dao.getById(id) ?: return
        dao.update(task.copy(isComplete = !task.isComplete))
    }
}
