package com.example.comeback.ui.todo.data

import android.content.Context
import androidx.room.Room

object DatabaseProvider {

    fun provideDatabase(context: Context): ToDoDatabase {
        return Room.databaseBuilder(
            context,
            ToDoDatabase::class.java,
            "todo_database"
        ).build()
    }
}