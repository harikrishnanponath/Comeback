package com.example.comeback.ui.todo.data

import kotlinx.coroutines.flow.Flow

class ToDoRepository(
    private val toDoDao: ToDoDao
) {

    val todos: Flow<List<ToDo>> = toDoDao.getAllTodos()
    suspend fun addTodo(todo: ToDo) = toDoDao.insertTodo(todo)
    suspend fun updateTodo(todo: ToDo) = toDoDao.updateTodo(todo)
    suspend fun deleteTodo(todo: ToDo) = toDoDao.deleteTodo(todo)
    suspend fun deleteCompletedTodos() { toDoDao.deleteCompletedTodos() }
}