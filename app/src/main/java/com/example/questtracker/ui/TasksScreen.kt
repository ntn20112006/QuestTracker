package com.example.questtracker.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.questtracker.QuestTrackerApp
import com.example.questtracker.data.ToDoTaskRepository
import com.example.questtracker.data.entity.ToDoTask
import com.example.questtracker.viewmodels.ToDoTasksViewModel
import com.example.questtracker.viewmodels.ToDoTasksViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(app: QuestTrackerApp = LocalContext.current.applicationContext as QuestTrackerApp) {
    val repository = remember { ToDoTaskRepository(app.database.toDoTaskDao()) }
    val factory = remember { ToDoTasksViewModelFactory(repository) }
    val viewModel: ToDoTasksViewModel = viewModel(factory = factory)
    val tasks by viewModel.tasks.collectAsState()

    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Tasks") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, null)
            }
        }
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            items(tasks, key = { it.id }) { task ->
                TaskRow(task) { viewModel.toggle(task.id) }
            }
        }
    }

    if (showDialog) AddTaskDialog(
        onAdd = {
            viewModel.add(it)
            showDialog = false
        },
        onDismiss = { showDialog = false }
    )
}


@Composable
private fun TaskRow(task: ToDoTask, onToggle: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable { onToggle() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = task.isComplete,
            onCheckedChange = { onToggle() }
        )
        Spacer(Modifier.width(12.dp))
        Text(
            task.title,
            style = MaterialTheme.typography.bodyLarge,
            textDecoration = if (task.isComplete) TextDecoration.LineThrough else null
        )
    }
}

@Composable
private fun AddTaskDialog(
    onAdd: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var text by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("New Task") },
        text = {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Title") },
                singleLine = true
            )
        },
        confirmButton = {
            TextButton(
                onClick = { if (text.isNotBlank()) onAdd(text.trim()) },
                enabled = text.isNotBlank()
            ) { Text("Add") }
        },
        dismissButton = {
            TextButton(onDismiss) { Text("Cancel") }
        }
    )
}
