package com.example.comeback.ui.todo.screens


import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.comeback.ui.theme.AppGreen
import com.example.comeback.ui.todo.data.ToDo
import com.example.comeback.ui.todo.event.ToDoEvent
import com.example.comeback.ui.todo.event.ToDoUiEvent
import com.example.comeback.ui.todo.screens.components.ToDoItem
import com.example.comeback.ui.todo.viewmodel.ToDoViewModel
import kotlinx.coroutines.launch

@Composable
fun ToDoListAScreen(
    modifier: Modifier = Modifier,
    viewModel: ToDoViewModel = viewModel(),
    snackbarHostState: SnackbarHostState
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val toDoList = uiState.todos
    val completedTasks = toDoList.filter { it.isChecked }
    val incompleteTasks = toDoList.filter { !it.isChecked }

    var newToDo by rememberSaveable { mutableStateOf("") }
    var editedText by rememberSaveable { mutableStateOf("") }

    val focusManager = LocalFocusManager.current
    val editFieldFocusRequester = remember { FocusRequester() }
    val scope = rememberCoroutineScope()

    var todoToEdit by rememberSaveable { mutableStateOf<ToDo?>(null) }

    val progress by animateFloatAsState(
        targetValue = if (toDoList.isEmpty()) 0f
        else completedTasks.size.toFloat() / toDoList.size,
        label = "progress"
    )

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is ToDoUiEvent.ShowSnackbar -> snackbarHostState.showSnackbar(event.message)
            }
        }
    }

    fun startEdit(todo: ToDo) {
        todoToEdit = todo
        editedText = todo.text
    }

    // Editing a task
    todoToEdit?.let { pending ->
        AlertDialog(
            onDismissRequest = {
                todoToEdit = null
                editedText = ""
            },
            shape = RoundedCornerShape(24.dp),
            title = { Text("Edit task", fontWeight = FontWeight.SemiBold) },
            text = {
                OutlinedTextField(
                    value = editedText,
                    onValueChange = { editedText = it },
                    label = { Text("Task") },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(editFieldFocusRequester),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AppGreen,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                        cursorColor = AppGreen
                    ),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (editedText.isNotBlank()) {
                                viewModel.onEvent(ToDoEvent.EditToDo(toDo = pending, editedText = editedText.trim()))
                                todoToEdit = null
                            }
                        }
                    )
                )
            },
            confirmButton = {
                TextButton(
                    enabled = editedText.isNotBlank(),
                    onClick = {
                        viewModel.onEvent(ToDoEvent.EditToDo(toDo = pending, editedText = editedText.trim()))
                        todoToEdit = null
                    }
                ) {
                    Text("Save", color = AppGreen, fontWeight = FontWeight.SemiBold)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        todoToEdit = null
                        editedText = ""
                    }
                ) {
                    Text("Cancel")
                }
            }
        )

        LaunchedEffect(pending) {
            editFieldFocusRequester.requestFocus()
        }
    }

    // Shows a snackbar with an Undo action. Any snackbar already on screen is replaced,
    // so quick successive deletes don't queue up.
    fun showUndoSnackbar(message: String, onUndo: () -> Unit) {
        scope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()
            val result = snackbarHostState.showSnackbar(
                message = message,
                actionLabel = "Undo",
                duration = SnackbarDuration.Short
            )
            if (result == SnackbarResult.ActionPerformed) onUndo()
        }
    }

    fun deleteWithUndo(todo: ToDo) {
        viewModel.onEvent(ToDoEvent.DeleteToDo(todo))
        showUndoSnackbar("Task deleted") {
            viewModel.onEvent(ToDoEvent.RestoreToDo(todo))
        }
    }

    fun clearCompletedWithUndo() {
        val cleared = completedTasks
        viewModel.onEvent(ToDoEvent.ClearCompleteToDo)
        showUndoSnackbar("${cleared.size} completed cleared") {
            cleared.forEach { viewModel.onEvent(ToDoEvent.RestoreToDo(it)) }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .padding(top = 16.dp)
    ) {
        Header(
            done = completedTasks.size,
            total = toDoList.size,
            progress = progress,
            isTasksEmpty = toDoList.isEmpty()
        )

        Spacer(modifier = Modifier.height(20.dp))

        AddTaskRow(
            value = newToDo,
            onValueChange = { newToDo = it },
            onSubmit = {
                submitTask(newToDo, focusManager) { text ->
                    viewModel.onEvent(ToDoEvent.AddToDo(text))
                    newToDo = ""
                }
            }
        )

        if (toDoList.isEmpty()) {
            EmptyState(modifier = Modifier.weight(1f))
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(top = 8.dp, bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (incompleteTasks.isNotEmpty()) {
                    item {
                        SectionHeader(title = "To do", count = incompleteTasks.size)
                    }
                    items(incompleteTasks) { todo ->
                        ToDoItem(
                            todo = todo,
                            onCheckedChange = { isChecked ->
                                viewModel.onEvent(ToDoEvent.UpdateToDo(todo, isChecked))
                            },
                            onDelete = { deleteWithUndo(todo) },
                            onEdit = {
                                todoToEdit = todo
                                editedText = todo.text
                            }
                        )
                    }
                }

                if (completedTasks.isNotEmpty()) {
                    item {
                        SectionHeader(
                            title = "Completed",
                            count = completedTasks.size,
                            action = {
                                TextButton(
                                    onClick = { clearCompletedWithUndo() }
                                ) {
                                    Text("Clear all", color = AppGreen)
                                }
                            }
                        )
                    }
                    items(completedTasks) { todo ->
                        ToDoItem(
                            todo = todo,
                            onCheckedChange = { isChecked ->
                                viewModel.onEvent(ToDoEvent.UpdateToDo(todo, isChecked))
                            },
                            onDelete = { deleteWithUndo(todo) },
                            onEdit = {
                                todoToEdit = todo
                                editedText = todo.text
                            }
                        )
                    }
                }
            }
        }
    }
}


private fun submitTask(
    text: String,
    focusManager: FocusManager,
    onValid: (String) -> Unit
) {
    if (text.isNotBlank()) {
        onValid(text.trim())
        focusManager.clearFocus()
    }
}

@Composable
private fun Header(done: Int, total: Int, progress: Float, isTasksEmpty: Boolean) {
    Column {
        Text(
            text = "My tasks",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = when {
                total == 0 -> "Nothing planned yet"
                done == total -> "All $total done. Nice work."
                else -> "$done of $total done"
            },
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(12.dp))

        if (!isTasksEmpty) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(CircleShape)
                .background(AppGreen.copy(alpha = 0.15f))
        ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth(progress)
                        .fillMaxHeight()
                        .clip(CircleShape)
                        .background(AppGreen)
                )
            }
        }
    }
}

@Composable
private fun AddTaskRow(
    value: String,
    onValueChange: (String) -> Unit,
    onSubmit: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text("Add a task") },
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.weight(1f),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AppGreen,
                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                cursorColor = AppGreen
            ),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = { onSubmit() })
        )
        Spacer(modifier = Modifier.width(10.dp))
        Button(
            onClick = onSubmit,
            enabled = value.isNotBlank(),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AppGreen,
                contentColor = Color.White,
                disabledContainerColor = AppGreen.copy(alpha = 0.3f),
                disabledContentColor = Color.White.copy(alpha = 0.8f)
            )
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text("Add", fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun SectionHeader(
    title: String,
    count: Int,
    action: (@Composable () -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(AppGreen.copy(alpha = 0.15f))
                .padding(horizontal = 8.dp, vertical = 2.dp)
        ) {
            Text(
                text = "$count",
                style = MaterialTheme.typography.labelMedium,
                color = AppGreen,
                fontWeight = FontWeight.SemiBold
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        action?.invoke()
    }
}

@Composable
private fun EmptyState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(AppGreen.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = AppGreen,
                modifier = Modifier.size(32.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No tasks yet",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Type a task above and tap Add.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}