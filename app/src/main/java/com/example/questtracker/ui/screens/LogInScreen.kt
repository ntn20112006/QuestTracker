package com.example.questtracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
//    onLoginClick: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isEmailError by remember { mutableStateOf(false) }
    var isPasswordError by remember { mutableStateOf(false) }
    var isPasswordTooLong by remember { mutableStateOf(false) }
    val MAX_PASSWORD_LENGTH = 20

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome Back!",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { 
                email = it
                isEmailError = false
            },
            label = { Text("Email") },
            isError = isEmailError,
            supportingText = {
                if (isEmailError) {
                    Text("Please enter a valid email")
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { 
                password = it
                isPasswordError = false
                isPasswordTooLong = false
            },
            label = { Text("Password") },
            isError = isPasswordError || isPasswordTooLong,
            supportingText = {
                Column {
                    if (isPasswordError) {
                        Text("Password must be at least 6 characters")
                    }
                    if (isPasswordTooLong) {
                        Text("Please enter a valid password")
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = "${password.length}/$MAX_PASSWORD_LENGTH",
                            color = if (password.length > MAX_PASSWORD_LENGTH) Color.Red else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        )

        Button(
            onClick = {
                isEmailError = email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
                isPasswordError = password.length < 6
                isPasswordTooLong = password.length > MAX_PASSWORD_LENGTH
                
                if (!isEmailError && !isPasswordError && !isPasswordTooLong) {
//                    onLoginClick(email, password)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Login")
        }

        TextButton(
            onClick = { /* TODO: Implement forgot password */ },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Forgot Password?")
        }
    }
}
