package com.example.comeback.ui.todo.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ToDo::class],
    version = 1,
    exportSchema = false
)
abstract class ToDoDatabase : RoomDatabase() {

    abstract fun todoDao(): ToDoDao
}