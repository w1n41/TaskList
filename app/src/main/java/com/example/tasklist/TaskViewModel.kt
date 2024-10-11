package com.example.tasklist

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TaskViewModel: ViewModel(

) {
    private var repository = TaskRepository()
    private var _list = MutableStateFlow(repository.listOfTasks)
    var taskText = repository.taskText
    var list = _list.asStateFlow()

    fun addNewTask(){
        repository.addNewTask()
        _list.value = repository.listOfTasks
        repository.taskText.value = ""
    }

    fun removeTask(task: TaskModel){
        repository.removeTask(task)
        _list.value = repository.listOfTasks
    }

    fun editTask(task: TaskModel, isEditing: Boolean){
        repository.editTask(task, isEditing)
    }

    fun onDismiss(task: TaskModel){
        repository.onDismiss(task)
    }
}

