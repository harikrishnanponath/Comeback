package com.example.comeback.ui.todo.data

data class ToDoUiState(
    val todos: List<ToDo> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
