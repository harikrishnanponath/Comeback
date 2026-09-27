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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.comeback.ui.todo.screens.ToDoListAScreen
import com.example.comeback.ui.theme.ComebackTheme
import com.example.comeback.ui.todo.viewmodel.ToDoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {

            val viewModel: ToDoViewModel = hiltViewModel()

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
