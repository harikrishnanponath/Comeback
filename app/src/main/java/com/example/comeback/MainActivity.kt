package com.example.comeback

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.comeback.ui.todo.screens.ToDoListAScreen
import com.example.comeback.ui.theme.ComebackTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComebackTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                   // ProfileScreen(modifier = Modifier.padding(innerPadding))
                    //LoginScreen(modifier = Modifier.padding(innerPadding))
                    ToDoListAScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComebackTheme {
        Greeting("Android", modifier = Modifier.padding(4.dp))
    }
}