package com.example.comeback.ui.todo.data

data class ToDo(
    val id: String = java.util.UUID.randomUUID().toString(),
    val text: String,
    val isChecked: Boolean = false
)
