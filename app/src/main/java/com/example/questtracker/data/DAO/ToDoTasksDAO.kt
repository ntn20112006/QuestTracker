package com.example.questtracker.data.DAO

import androidx.room.*
import com.example.questtracker.data.entity.ToDoTask
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoTaskDao {
    @Query("SELECT * FROM todoTasks")
    fun getAll(): Flow<List<ToDoTask>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: ToDoTask)

    @Update
    suspend fun update(task: ToDoTask)

    @Delete
    suspend fun delete(task: ToDoTask)

    @Query("SELECT * FROM todoTasks WHERE id = :id")
    suspend fun getById(id: Int): ToDoTask?
}
