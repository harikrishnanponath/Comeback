package com.example.comeback.ui.todo.data

import android.content.Context
import androidx.annotation.UiContext
import androidx.core.content.ContextCompat
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ToDoModule {

    @Provides
    @Singleton
    fun provideToDoDatabase(
         @ApplicationContext context: Context
    ) : ToDoDatabase {
        return Room.databaseBuilder(
            context,
            ToDoDatabase::class.java,
            "todo_database"
        ).build()
    }

    @Provides
    fun provideToDoDao(
        database: ToDoDatabase
    ) : ToDoDao{
        return database.todoDao()
    }
}