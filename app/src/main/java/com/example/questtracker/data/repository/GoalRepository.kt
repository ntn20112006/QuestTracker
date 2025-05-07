package com.example.questtracker.data.repository

import com.example.questtracker.data.DAO.GoalDao
import com.example.questtracker.data.entity.Goal
import kotlinx.coroutines.flow.Flow

/**
 * Repository class for managing goals in the QuestTracker application.
 * This class serves as a single source of truth for goal data and provides
 * a clean API for the ViewModel to interact with the data layer.
 *
 * @property dao The Data Access Object for performing database operations on goals
 */
class GoalRepository(private val dao: GoalDao) {
    /**
     * Flow that emits the current list of goals.
     * The list is updated whenever the underlying data changes.
     */
    val goals: Flow<List<Goal>> = dao.getAll()

    /**
     * Adds a new goal to the database.
     *
     * @param goal The goal to add
     */
    suspend fun add(goal: Goal) = dao.insert(goal)

    /**
     * Deletes a goal from the database.
     *
     * @param goal The goal to delete
     */
    suspend fun delete(goal: Goal) = dao.delete(goal)

    /**
     * Updates an existing goal in the database.
     *
     * @param goal The goal to update
     */
    suspend fun update(goal: Goal) = dao.update(goal)

    /**
     * Toggles the completion status of a goal.
     * This operation is performed directly in the database for better performance.
     *
     * @param id The ID of the goal to toggle
     */
    suspend fun toggle(id: Int) = dao.toggle(id)
}
