package com.example.comeback.ui.todo.screens

import android.util.Log
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.comeback.ui.theme.AppGreen
import com.example.comeback.ui.todo.data.ToDo
import com.example.comeback.ui.todo.screens.components.ToDoItem

@Composable
fun ToDoListAScreen(modifier: Modifier = Modifier) {

    var toDoList by rememberSaveable { mutableStateOf(listOf<ToDo>()) }
    var newToDo by rememberSaveable { mutableStateOf("") }
    val completedTasks = toDoList.filter { it.isChecked }
    val unCompletedTasks = toDoList.filter { !it.isChecked }


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = newToDo,
                onValueChange = { newToDo = it },
                label = { Text("New ToDo") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AppGreen,
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = AppGreen,
                    unfocusedLabelColor = Color.Gray,
                    cursorColor = AppGreen
                ),
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if (newToDo.isNotBlank()) {
                        toDoList = toDoList + ToDo(newToDo)
                        newToDo = ""
                    }
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = AppGreen, contentColor = Color.White
                )
            ) {
                Text("Add")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        LazyColumn {
            if (unCompletedTasks.isNotEmpty()) {
                item {
                    Text(
                        text = "${toDoList.size - completedTasks.size} Incomplete Tasks",
                        style = MaterialTheme.typography.titleMedium,
                        textDecoration = TextDecoration.Underline
                    )
                }
                items(unCompletedTasks) { todo ->
                    ToDoItem(
                        todo = todo,

                        onCheckedChange = { isChecked ->
                            toDoList = updateToDo(toDoList, todo, isChecked)
                        },
                        onDelete = {
                            toDoList = toDoList - todo
                        }
                    )
                }
            }
            if (completedTasks.isNotEmpty()) {
                item {
                    Text(
                        text = "${completedTasks.size} Completed Tasks",
                        style = MaterialTheme.typography.titleMedium,
                        textDecoration = TextDecoration.Underline
                    )
                }
                items(completedTasks) { todo ->
                    ToDoItem(
                        todo = todo,

                        onCheckedChange = { isChecked ->
                            toDoList = updateToDo(toDoList, todo, isChecked)
                        },
                        onDelete = {
                            toDoList = toDoList - todo
                        }
                    )

                }
            }

        }
    }
}

fun updateToDo(
    toDoList: List<ToDo>,
    todo: ToDo,
    isChecked: Boolean
): List<ToDo> {
    return toDoList.map {
        if (it == todo) it.copy(isChecked = isChecked)
        else it
    }
}