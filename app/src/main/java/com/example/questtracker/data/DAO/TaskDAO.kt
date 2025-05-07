package com.example.questtracker.data.DAO

import androidx.room.*
import com.example.questtracker.data.entity.Task
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for managing ToDoTask entities in the database.
 * This interface defines methods for performing CRUD operations on tasks.
 */
@Dao
interface TaskDao {
    /**
     * Retrieves all tasks from the database as a Flow of lists.
     * The Flow will emit a new list whenever the underlying data changes.
     *
     * @return A Flow emitting a list of all ToDoTask entities
     */
    @Query("SELECT * FROM todoTasks")
    fun getAll(): Flow<List<Task>>

    /**
     * Inserts a new task into the database or replaces an existing one if there's a conflict.
     *
     * @param task The ToDoTask entity to insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    /**
     * Updates an existing task in the database.
     *
     * @param task The ToDoTask entity to update
     */
    @Update
    suspend fun update(task: Task)

    /**
     * Deletes a task from the database.
     *
     * @param task The ToDoTask entity to delete
     */
    @Delete
    suspend fun delete(task: Task)

    /**
     * Retrieves a specific task by its ID.
     *
     * @param id The ID of the task to retrieve
     * @return The ToDoTask entity if found, null otherwise
     */
    @Query("SELECT * FROM todoTasks WHERE id = :id")
    suspend fun getById(id: Int): Task?
}
