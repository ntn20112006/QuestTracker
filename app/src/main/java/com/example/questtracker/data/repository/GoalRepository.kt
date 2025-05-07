package com.example.questtracker.data.repository

import com.example.questtracker.data.DAO.GoalDao
import com.example.questtracker.data.entity.Goal
import kotlinx.coroutines.flow.Flow

class GoalRepository(private val dao: GoalDao) {
    val goals: Flow<List<Goal>> = dao.getAll()

    suspend fun add(goal: Goal) = dao.insert(goal)
    suspend fun toggle(id: Int) = dao.toggle(id)
    suspend fun update(goal: Goal) = dao.update(goal)
    suspend fun delete(goal: Goal) = dao.delete(goal)
}
