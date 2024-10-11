package com.example.tasklist


data class TaskModel(
    var id: Int = 1,
    var task: String = "",
    var isEditing: Boolean = false
)