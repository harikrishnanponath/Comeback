package com.example.comeback.ui.todo.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comeback.ui.todo.data.ToDo
import com.example.comeback.ui.todo.data.ToDoRepository
import com.example.comeback.ui.todo.data.ToDoUiState
import com.example.comeback.ui.todo.event.ToDoEvent
import com.example.comeback.ui.todo.event.ToDoUiEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.collections.emptyList

class ToDoViewModel(
    private val toDoRepository: ToDoRepository
) : ViewModel() {


    private val _uiState = MutableStateFlow(ToDoUiState())
    val uiState: StateFlow<ToDoUiState> = _uiState

    private val _uiEvent = MutableSharedFlow<ToDoUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()


    init {
        viewModelScope.launch {
            toDoRepository.todos.collect { todos ->
                _uiState.update {
                    it.copy(todos = todos)
                }
            }
        }
    }


    fun addToDo(toDoText: String) {
        if (toDoText.isBlank()) return
        val todo = ToDo(text = toDoText)

        viewModelScope.launch {
            toDoRepository.addTodo(todo)
            _uiEvent.emit(ToDoUiEvent.ShowSnackbar("Task Added"))
        }
    }

    fun deleteToDo(toDo: ToDo) {

        viewModelScope.launch {
            toDoRepository.deleteTodo(toDo)
        }
    }

    fun updateTodo(todo: ToDo, isChecked: Boolean) {

        viewModelScope.launch {
            val updatedTodo = todo.copy(
                isChecked = isChecked
            )
            toDoRepository.updateTodo(updatedTodo)
        }
    }

    fun editToDo(toDo: ToDo, editedText: String) {

        val newText = editedText.trim()

        if (newText.isBlank()) return
        if (newText == toDo.text.trim()) return

        val updatedTodo = toDo.copy(
            text = newText
        )

        viewModelScope.launch {
            toDoRepository.updateTodo(updatedTodo)
            _uiEvent.emit(ToDoUiEvent.ShowSnackbar("Task Updated"))
        }
    }

    fun onEvent(event: ToDoEvent) {
        when (event) {
            is ToDoEvent.AddToDo -> {
                addToDo(event.text)

            }
            is ToDoEvent.DeleteToDo -> {
                deleteToDo(event.toDo)
            }
            is ToDoEvent.UpdateToDo -> {
                updateTodo(event.toDo, event.isChecked)
            }
            is ToDoEvent.EditToDo -> {
                editToDo(
                    toDo = event.toDo,
                    editedText = event.editedText
                )
            }

            is ToDoEvent.RestoreToDo -> {
                viewModelScope.launch {
                    toDoRepository.addTodo(event.toDo)
                }
            }

            is ToDoEvent.ClearCompleteToDo -> {
                viewModelScope.launch {
                    toDoRepository.deleteCompletedTodos()
                    _uiEvent.emit(ToDoUiEvent.ShowSnackbar("Completed Tasks Cleared"))
                }
            }
        }
    }
}

