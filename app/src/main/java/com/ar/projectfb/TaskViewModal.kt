package com.ar.projectfb

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModal(application: Application) : AndroidViewModel(application){
    val allTask: LiveData<List<Task>>
    val repository: TaskRepository

    init {
        val dao = TaskDataBase.getDatabase(application).getTasksDao()
        repository = TaskRepository(dao)
        allTask = repository.allTasks
    }

    fun deleteTask (task: Task) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(task)
    }

    fun updateTask(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(task)
    }

    fun addTask(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(task)
    }

}