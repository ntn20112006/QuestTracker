package com.example.questtracker.ui.screens

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
import com.example.questtracker.data.Date
import com.example.questtracker.data.entity.Goal
import com.example.questtracker.data.repository.GoalRepository
import com.example.questtracker.viewmodels.GoalViewModel
import com.example.questtracker.viewmodels.GoalViewModelFactory

/**
 * Main screen for managing goals in the QuestTracker application.
 * This screen displays a list of goals and provides functionality to add, edit, delete, and toggle goals.
 *
 * @param app The QuestTrackerApp instance, retrieved from the current context
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalsScreen(
    app: QuestTrackerApp = LocalContext.current.applicationContext as QuestTrackerApp
) {
    val repo = remember { GoalRepository(app.database.goalDao()) }
    val factory = remember { GoalViewModelFactory(repo) }
    val viewModel: GoalViewModel = viewModel(factory = factory)
    val goals by viewModel.goals.collectAsState()

    var showAddDialog by remember { mutableStateOf(false) }
    var editGoal by remember { mutableStateOf<Goal?>(null) }
    var deleteGoal by remember { mutableStateOf<Goal?>(null) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Goals") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Goal")
            }
        }
    ) { padding ->
        LazyColumn(contentPadding = padding, modifier = Modifier.fillMaxSize()) {
            items(goals, key = { it.id }) { goal ->
                GoalCard(
                    goal = goal,
                    onToggle = { viewModel.toggle(goal.id) },
                    onEdit = { editGoal = it },
                    onDelete = { deleteGoal = it }
                )
            }
        }
    }

    if (showAddDialog) {
        GoalDialog(
            initial = null,
            onSubmit = {
                viewModel.add(it)
                showAddDialog = false
            },
            onDismiss = { showAddDialog = false }
        )
    }

    editGoal?.let { orig ->
        GoalDialog(
            initial = orig,
            onSubmit = {
                viewModel.update(it)
                editGoal = null
            },
            onDismiss = { editGoal = null }
        )
    }

    deleteGoal?.let { toDelete ->
        AlertDialog(
            onDismissRequest = { deleteGoal = null },
            title = { Text("Delete goal?") },
            text = { Text("Are you sure you want to delete ${toDelete.title}?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.delete(toDelete)
                    deleteGoal = null
                }) { Text("Delete") }
            },
            dismissButton = {
                TextButton(onClick = { deleteGoal = null }) { Text("Cancel") }
            }
        )
    }
}

/**
 * A card component that displays a single goal with its details and actions.
 *
 * @param goal The goal to display
 * @param onToggle Callback when the goal's completion status is toggled
 * @param onEdit Callback when the goal is edited
 * @param onDelete Callback when the goal is deleted
 */
@Composable
private fun GoalCard(
    goal: Goal,
    onToggle: () -> Unit,
    onEdit: (Goal) -> Unit,
    onDelete: (Goal) -> Unit
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
                    text = goal.title,
                    style = MaterialTheme.typography.bodyMedium,
                    textDecoration = if (goal.isComplete) TextDecoration.LineThrough else null,
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
                        goal.deadline.month,
                        goal.deadline.day,
                        goal.deadline.year
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
                                onEdit(goal)
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Delete") },
                            onClick = {
                                menuExpanded = false
                                onDelete(goal)
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
                    text = goal.description,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
    }
}

/**
 * A dialog for adding or editing a goal.
 *
 * @param initial The initial goal data when editing, null when adding a new goal
 * @param onSubmit Callback when the goal is submitted
 * @param onDismiss Callback when the dialog is dismissed
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GoalDialog(
    initial: Goal?,
    onSubmit: (Goal) -> Unit,
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

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (initial == null) "New Goal" else "Edit Goal") },
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
                        Goal(
                            id = initial?.id ?: 0,
                            title = title.trim(),
                            description = description.trim(),
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
