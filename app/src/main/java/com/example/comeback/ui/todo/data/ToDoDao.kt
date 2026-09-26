package com.example.comeback.ui.todo.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {

    @Insert
    suspend fun insertTodo(todo: ToDo)

    @Update
    suspend fun updateTodo(todo: ToDo)

    @Delete
    suspend fun deleteTodo(todo: ToDo)

    @Query("SELECT * FROM todos")
    fun getAllTodos(): Flow<List<ToDo>>

    @Query("DELETE FROM todos WHERE isChecked = 1")
    suspend fun deleteCompletedTodos()
}