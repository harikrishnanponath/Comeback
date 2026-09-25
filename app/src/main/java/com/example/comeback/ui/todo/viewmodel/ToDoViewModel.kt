package com.example.comeback.ui.todo.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comeback.ui.todo.data.ToDo
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

class ToDoViewModel : ViewModel() {


    private val _uiState = MutableStateFlow(ToDoUiState())
    val uiState: StateFlow<ToDoUiState> = _uiState

    private val _uiEvent = MutableSharedFlow<ToDoUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()


    fun addToDo(toDoText: String) {
        if (toDoText.isBlank()) return

        _uiState.update { currentList ->
                currentList.copy(
                    todos = currentList.todos + ToDo(text = toDoText)
                )
        }

        viewModelScope.launch {
            _uiEvent.emit(ToDoUiEvent.ShowSnackbar("Task Added"))
        }
    }

    fun deleteToDo(toDo: ToDo) {
        _uiState.update { currentList ->
            currentList.copy(
                todos = currentList.todos - toDo
            )
        }
    }

    fun updateTodo(todo: ToDo, isChecked: Boolean) {
        _uiState.update { currentList ->
            currentList.copy(
                todos = currentList.todos.map {
                    if (it == todo) {
                        it.copy(isChecked = isChecked)
                    } else {
                        it
                    }
                }
            )
        }
    }

    fun editToDo(toDo: ToDo, editedText: String) {

        if (editedText.isBlank()) return
        if (editedText == toDo.text) return

        _uiState.update { currentList ->
            currentList.copy(
                todos = currentList.todos.map {
                    if (it == toDo) {
                        it.copy(text = editedText)
                    } else {
                        it
                    }
                }
            )
    }

        viewModelScope.launch {
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
                _uiState.update { currentList ->
                    currentList.copy(
                        todos = currentList.todos + event.toDo
                    )
                }
            }

            is ToDoEvent.ClearCompleteToDo -> {
                _uiState.update { currentList ->
                    currentList.copy(
                        todos = currentList.todos.filter { !it.isChecked }
                    )
                }

                viewModelScope.launch {
                    _uiEvent.emit(ToDoUiEvent.ShowSnackbar("Completed Tasks Cleared"))
                }
            }
        }
    }
}

