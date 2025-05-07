package com.example.questtracker.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.questtracker.QuestTrackerApp
import com.example.questtracker.data.repository.TaskRepository
import com.example.questtracker.data.entity.Task
import com.example.questtracker.data.Date
import com.example.questtracker.viewmodels.TaskViewModel
import com.example.questtracker.viewmodels.TaskViewModelFactory

/**
 * Main screen for managing tasks in the QuestTracker application.
 * This screen displays a list of tasks and provides functionality to add, edit, delete, and toggle tasks.
 * Tasks can be recurring and have deadlines.
 *
 * @param app The QuestTrackerApp instance, retrieved from the current context
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(
    app: QuestTrackerApp = LocalContext.current.applicationContext as QuestTrackerApp
) {
    val repo       = remember { TaskRepository(app.database.taskDao()) }
    val factory    = remember { TaskViewModelFactory(repo) }
    val viewModel: TaskViewModel = viewModel(factory = factory)
    val tasks by viewModel.tasks.collectAsState()

    var showAddDialog by remember { mutableStateOf(false) }
    var editTask by remember { mutableStateOf<Task?>(null) }
    var deleteTask by remember { mutableStateOf<Task?>(null) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Tasks") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Task")
            }
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.fillMaxSize()
        ) {
            items(tasks, key = { it.id }) { task ->
                TaskCard(
                    task = task,
                    onToggle = { viewModel.toggle(task.id) },
                    onEdit = { editTask = it },
                    onDelete = { deleteTask = it }
                )
            }
        }
    }

    if (showAddDialog) {
        TaskDialog(
            initial = null,
            onSubmit = {
                viewModel.add(it)
                showAddDialog = false
            },
            onDismiss = { showAddDialog = false }
        )
    }

    editTask?.let { orig ->
        TaskDialog(
            initial = orig,
            onSubmit = { updated ->
                viewModel.update(updated)
                editTask = null
            },
            onDismiss = { editTask = null }
        )
    }

    deleteTask?.let { toDelete ->
        AlertDialog(
            onDismissRequest = { deleteTask = null },
            title = { Text("Delete task?") },
            text = { Text("Are you sure you want to delete ${toDelete.title}?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.delete(toDelete)
                    deleteTask = null
                }) { Text("Delete") }
            },
            dismissButton = {
                TextButton(onClick = { deleteTask = null }) { Text("Cancel") }
            }
        )
    }
}

/**
 * A card component that displays a single task with its details and actions.
 * The card shows the task's title, deadline, and description, with options to edit or delete.
 *
 * @param task The task to display
 * @param onToggle Callback when the task's completion status is toggled
 * @param onEdit Callback when the task is edited
 * @param onDelete Callback when the task is deleted
 */
@Composable
private fun TaskCard(
    task: Task,
    onToggle: () -> Unit,
    onEdit: (Task) -> Unit,
    onDelete: (Task) -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onToggle() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.bodyMedium,
                    textDecoration = if (task.isComplete) TextDecoration.LineThrough else null,
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(50)
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                )

                Spacer(Modifier.width(8.dp))

                Text(
                    text = "%02d-%02d-%04d".format(
                        task.deadline.month,
                        task.deadline.day,
                        task.deadline.year
                    ),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(50)
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                )

                Spacer(Modifier.weight(1f))

                Box {
                    IconButton(onClick = { menuExpanded = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More")
                    }
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Edit") },
                            onClick = {
                                menuExpanded = false
                                onEdit(task)
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Delete") },
                            onClick = {
                                menuExpanded = false
                                onDelete(task)
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.surfaceVariant,
                tonalElevation = 2.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = task.description,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
    }
}

/**
 * A dialog for adding or editing a task.
 * The dialog includes fields for title, description, deadline, and recurring task settings.
 *
 * @param initial The initial task data when editing, null when adding a new task
 * @param onSubmit Callback when the task is submitted
 * @param onDismiss Callback when the dialog is dismissed
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TaskDialog(
    initial: Task?,
    onSubmit: (Task) -> Unit,
    onDismiss: () -> Unit
) {
    var title by remember { mutableStateOf(initial?.title.orEmpty()) }
    var description by remember { mutableStateOf(initial?.description.orEmpty()) }
    var dateText by remember {
        mutableStateOf(
            initial?.deadline?.let {
                "%02d-%02d-%04d".format(it.month, it.day, it.year)
            }.orEmpty()
        )
    }
    var isRecurring by remember { mutableStateOf(initial?.isRecurring ?: false) }
    var recurrenceInterval by remember {
        mutableStateOf(initial?.recurrenceInterval?.toString().orEmpty())
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (initial == null) "New Task" else "Edit Task") },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = dateText,
                    onValueChange = { dateText = it },
                    label = { Text("Deadline (MM-DD-YYYY)") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                )

                Spacer(Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = isRecurring,
                        onCheckedChange = { isRecurring = it }
                    )
                    Spacer(Modifier.width(4.dp))
                    Text("Repeat every X days")
                }

                if (isRecurring) {
                    Spacer(Modifier.height(4.dp))
                    OutlinedTextField(
                        value = recurrenceInterval,
                        onValueChange = { recurrenceInterval = it.filter(Char::isDigit) },
                        label = { Text("Interval (days)") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val parts = dateText.split("-")
                    val m = parts.getOrNull(0)?.toIntOrNull() ?: 1
                    val d = parts.getOrNull(1)?.toIntOrNull() ?: 1
                    val y = parts.getOrNull(2)?.toIntOrNull() ?: 1970
                    val date = Date(m, d, y)

                    onSubmit(
                        Task(
                            id = initial?.id ?: 0,
                            title = title.trim(),
                            description = description.trim(),
                            isRecurring = isRecurring,
                            recurrenceInterval = recurrenceInterval.toIntOrNull() ?: 1,
                            deadline = date,
                            isComplete = initial?.isComplete ?: false
                        )
                    )
                    onDismiss()
                },
                enabled = title.isNotBlank() && Regex("\\d{2}-\\d{2}-\\d{4}").matches(dateText)
            ) {
                Text(if (initial == null) "Add" else "Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
