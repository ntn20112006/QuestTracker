package com.example.questtracker.data.DAO

import androidx.room.*
import com.example.questtracker.data.entity.Goal
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for managing Goal entities in the database.
 * This interface defines methods for performing CRUD operations on goals.
 */
@Dao
interface GoalDao {
    /**
     * Retrieves all goals from the database as a Flow of lists.
     * The Flow will emit a new list whenever the underlying data changes.
     *
     * @return A Flow emitting a list of all Goal entities
     */
    @Query("SELECT * FROM goals")
    fun getAll(): Flow<List<Goal>>

    /**
     * Inserts a new goal into the database or replaces an existing one if there's a conflict.
     *
     * @param goal The Goal entity to insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(goal: Goal)

    /**
     * Updates an existing goal in the database.
     *
     * @param goal The Goal entity to update
     */
    @Update
    suspend fun update(goal: Goal)

    /**
     * Deletes a goal from the database.
     *
     * @param goal The Goal entity to delete
     */
    @Delete
    suspend fun delete(goal: Goal)

    /**
     * Toggles the completion status of a goal.
     *
     * @param id The ID of the goal to toggle
     */
    @Query("UPDATE goals SET isComplete = NOT isComplete WHERE id = :id")
    suspend fun toggle(id: Int)
}
