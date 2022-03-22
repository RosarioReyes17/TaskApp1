package com.ar.projectfb

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: Task)


    @Update
    suspend fun update (task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("Select * from taskTable order by id ASC")
    fun getAllTasks(): LiveData<List<Task>>

    @Query("Select * from taskTable where user = :user order by id ASC")
    fun getAllTasksByUser(user: String): LiveData<List<Task>>
}