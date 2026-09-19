package com.example.comeback.ui.todo.event

sealed interface ToDoUiEvent {

    data class ShowSnackbar(
        val message: String
    ) : ToDoUiEvent
}