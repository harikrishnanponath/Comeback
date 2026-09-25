package com.example.comeback.ui.todo.screens.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.comeback.ui.theme.AppGreen
import com.example.comeback.ui.todo.data.ToDo

@Composable
fun ToDoItem(
    todo: ToDo,
    onCheckedChange: (Boolean) -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
    onEdit: () -> Unit
) {
    val textColor = if (todo.isChecked) {
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
    ) {
        Row(
            modifier = Modifier
                .toggleable(
                    value = todo.isChecked,
                    role = Role.Checkbox,
                    onValueChange = onCheckedChange
                )
                .padding(start = 14.dp, top = 4.dp, bottom = 4.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CheckCircle(checked = todo.isChecked)
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = todo.text,
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 10.dp),
                style = MaterialTheme.typography.bodyLarge,
                color = textColor,
                textDecoration = if (todo.isChecked) TextDecoration.LineThrough else TextDecoration.None
            )
                IconButton(onClick = onEdit) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit task",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                }

            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete task",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
private fun CheckCircle(checked: Boolean) {
    val fill = animateColorAsState(
        targetValue = if (checked) AppGreen else Color.Transparent,
        label = "checkFill"
    ).value
    val stroke = animateColorAsState(
        targetValue = if (checked) AppGreen else MaterialTheme.colorScheme.outline,
        label = "checkStroke"
    ).value

    Box(
        modifier = Modifier
            .size(24.dp)
            .clip(CircleShape)
            .background(fill)
            .border(BorderStroke(2.dp, stroke), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        if (checked) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}