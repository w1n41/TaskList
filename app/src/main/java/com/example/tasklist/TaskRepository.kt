package com.example.tasklist

import androidx.compose.runtime.mutableStateOf

class TaskRepository(
){
    var listOfTasks = mutableListOf<TaskModel>()
    var taskText = mutableStateOf("")
    var taskId = 1

    fun addNewTask() {
        listOfTasks.add(TaskModel(id = taskId++, taskText.value))
    }

    fun removeTask(task: TaskModel){
        listOfTasks.remove(task)
    }

    fun editTask(task: TaskModel, isEditing: Boolean){
        task.isEditing = isEditing
    }

    fun onDismiss(task: TaskModel){
        task.isEditing = false
    }
}