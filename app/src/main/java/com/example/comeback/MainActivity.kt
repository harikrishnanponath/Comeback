package com.example.comeback

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.comeback.ui.todo.screens.ToDoListAScreen
import com.example.comeback.ui.theme.ComebackTheme
import com.example.comeback.ui.todo.data.DatabaseProvider
import com.example.comeback.ui.todo.data.ToDoRepository
import com.example.comeback.ui.todo.viewmodel.ToDoViewModel
import com.example.comeback.ui.todo.viewmodel.ToDoViewModelFactory


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = DatabaseProvider.provideDatabase(applicationContext)
        val repository = ToDoRepository(database.todoDao())
        val factory = ToDoViewModelFactory(repository)



        enableEdgeToEdge()
        setContent {

            val viewModel: ToDoViewModel = viewModel(
                factory = factory
            )

            ComebackTheme {

                val snackbarHostState = remember {
                    SnackbarHostState()
                }

                Scaffold(modifier = Modifier.fillMaxSize(),
                    snackbarHost = {
                        SnackbarHost(hostState = snackbarHostState)
                    }

                ) { innerPadding ->

                    ToDoListAScreen(
                        modifier = Modifier.padding(innerPadding),
                        snackbarHostState = snackbarHostState,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}
