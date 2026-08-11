package com.example.comeback.ui.todo.event

import com.example.comeback.ui.todo.data.ToDo

sealed interface ToDoEvent {

    data class AddToDo(val text: String) : ToDoEvent
    data class DeleteToDo(val toDo: ToDo) : ToDoEvent
    data class UpdateToDo(
        val toDo: ToDo,
        val isChecked: Boolean
    ) : ToDoEvent

    data object ClearCompleteToDo: ToDoEvent
}