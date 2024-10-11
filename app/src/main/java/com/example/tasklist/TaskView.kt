package com.example.tasklist

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TasksView(taskViewModel: TaskViewModel) {

    var isAdding by remember {
        mutableStateOf(false)
    }

    val list = taskViewModel.list.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(Modifier.height(30.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isAdding == true) {
                AddTaskDialog(
                    taskViewModel,
                    onDismissRequest = {
                        isAdding = false
                    }
                )
            }
            Button(
                onClick = { isAdding = true }
            ) {
                Text("Add new task")
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(list.value) {task ->
                if(task.isEditing == true){
                    EditTaskDialog(
                        taskViewModel,
                        onDismissRequest = {
                            taskViewModel.onDismiss(task)
                        },
                        onEditDone = {taskText ->
                            taskViewModel.onDismiss(task)
                            task.task = taskText
                        }
                    )
                }
                else{
                    TaskCard(
                        task,
                        onTaskDone = {
                            taskViewModel.removeTask(task) },
                        onEditClicked = {taskViewModel.editTask(task, true)}
                    )
                    Spacer(Modifier.height(20.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskDialog(
    taskViewModel: TaskViewModel,
    onDismissRequest: () -> Unit,
) {
    var taskModel = TaskModel()

    BasicAlertDialog(
        onDismissRequest = onDismissRequest
    ) {
        Column {
            TextField(
                modifier = Modifier.clip(shape = RoundedCornerShape(15.dp)),
                onValueChange = {},
                label = { Text("Task id") },
                value = taskModel.id.toString()
            )
            Spacer(Modifier.height(8.dp))
            TextField(
                modifier = Modifier.clip(shape = RoundedCornerShape(15.dp)),
                onValueChange = {
                    taskViewModel.taskText.value = it
                },
                label = { Text("Task text") },
                value = taskViewModel.taskText.value
            )
            Spacer(Modifier.height(5.dp))
            Row {
                Button(
                    onClick = {
                        taskViewModel.addNewTask()
                        onDismissRequest()
                    }
                ) {
                    Text("Add task")
                }
            }
        }
    }
}

@Composable
fun TaskCard(
    taskModel: TaskModel,
    onTaskDone: () -> Unit,
    onEditClicked: () -> Unit
) {
    Card(
        modifier = Modifier
            .height(200.dp)
            .width(400.dp)
            .clip(shape = RoundedCornerShape(20.dp))
            .border(width = 2.dp, shape = RoundedCornerShape(20.dp), color = Color.Gray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(shape = RoundedCornerShape(20.dp))
        ) {
            Text(
                modifier = Modifier.padding(vertical = 20.dp, horizontal = 20.dp),
                text = taskModel.id.toString(),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
            Spacer(Modifier.height(10.dp))
            Text(
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp),
                text = taskModel.task,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(50.dp))
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.End
            ){
                IconButton(onClick = {
                    onEditClicked()
                }) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Edit task"
                    )
                }
                IconButton(onClick = {
                    onTaskDone()
                }) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "Task done"
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTaskDialog(
    taskViewModel: TaskViewModel,
    onDismissRequest: () -> Unit,
    onEditDone: (String) -> Unit
){
    var task = TaskModel()

    var taskText by remember {
        mutableStateOf(taskViewModel.taskText.value)
    }

    BasicAlertDialog(
        onDismissRequest = onDismissRequest
    ) {
        Column {
            TextField(
                modifier = Modifier.clip(shape = RoundedCornerShape(15.dp)),
                onValueChange = {},
                label = { Text("Task id") },
                value = task.id.toString()
            )
            Spacer(Modifier.height(8.dp))
            TextField(
                modifier = Modifier.clip(shape = RoundedCornerShape(15.dp)),
                onValueChange = {
                    taskViewModel.taskText.value = it
                },
                label = { Text("Task text") },
                value = taskText
            )
            Spacer(Modifier.height(5.dp))
            Row {
                Button(
                    onClick = {
                        onEditDone(taskText)
                    }
                ) {
                    Text("Edit task")
                }
            }
        }
    }
    TaskCard(
        task,
        onTaskDone = {
            taskViewModel.removeTask(task) },
        onEditClicked = {taskViewModel.editTask(task, true)}
    )
}


@Preview(showBackground = true)
@Composable
fun TasksPreview() {
    TaskCard(taskModel = TaskModel(), onTaskDone = {}, onEditClicked = {})
}