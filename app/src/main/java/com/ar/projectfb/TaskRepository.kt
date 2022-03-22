package com.ar.projectfb

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth

class TaskRepository (private val taskDao: TaskDao ){
    val allTasks: LiveData<List<Task>> = taskDao.getAllTasksByUser(FirebaseAuth.getInstance().currentUser!!.uid)

    suspend fun insert(task: Task) {
        taskDao.insert(task)
    }

    suspend fun delete(task: Task){
        taskDao.delete(task)
    }

    suspend fun update(task: Task){
        taskDao.update(task)
    }
}