package com.nahadayka.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nahadayka.R
import com.nahadayka.viewModel.NavigationState
import com.nahadayka.viewModel.NavigationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(state: NavigationState, authViewModel : NavigationViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var isLogin by remember { mutableStateOf(state.isLogin) }
        var emailValue by remember { mutableStateOf("") }
        var passwordValue by remember { mutableStateOf("") }
        var emailEmptyError by remember { mutableStateOf(false) }
        var emailValidError by remember { mutableStateOf(false) }
        var passwordEmptyError by remember { mutableStateOf(false) }
        var passwordValidError by remember { mutableStateOf(false) }
        var confirmPasswordValue by remember { mutableStateOf("") }
        var confirmPasswordEmptyError by remember { mutableStateOf(false) }
        var confirmPasswordValidError by remember { mutableStateOf(false) }
        var confirmDoublePasswordError by remember { mutableStateOf(false) }


        Text(
            modifier = Modifier.padding(bottom = 32.dp),
            text = if (isLogin) stringResource(R.string.title_login) else stringResource(R.string.title_register),
            fontWeight = FontWeight.W900,
            fontSize = 32.sp
        )
        OutlinedTextField(
            value = emailValue,
            onValueChange = { emailValue = it },
            label = { Text(text = stringResource(R.string.title_email)) },
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black,
                focusedLabelColor = Color.Black,
                focusedTextColor = Color.Black,
                cursorColor = Color.Black
            )
        )
        if (emailEmptyError) {
            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = stringResource(R.string.error_message_email_empty),
                fontSize = 10.sp,
                color = Color.Red
            )
        }
        if (emailValidError && !emailEmptyError) {
            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = stringResource(R.string.error_message_email_valid),
                fontSize = 10.sp,
                color = Color.Red
            )
        }
        OutlinedTextField(
            value = passwordValue,
            onValueChange = { passwordValue = it },
            label = { Text(text = stringResource(R.string.title_password)) },
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black,
                focusedLabelColor = Color.Black,
                focusedTextColor = Color.Black,
                cursorColor = Color.Black
            ),
            visualTransformation = PasswordVisualTransformation()
        )
        if (passwordEmptyError) {
            Text(
                modifier = Modifier.padding(0.dp),
                text = stringResource(R.string.error_message_password_empty),
                fontSize = 10.sp,
                color = Color.Red
            )
        }
        if (passwordValidError && !passwordEmptyError) {
            Text(
                modifier = Modifier.padding(0.dp),
                text = stringResource(R.string.error_message_password_valid),
                fontSize = 10.sp,
                color = Color.Red
            )

        }
        if (!isLogin) {
            OutlinedTextField(
                value = confirmPasswordValue,
                onValueChange = { confirmPasswordValue = it },
                label = { Text(text = stringResource(R.string.title_confirm_password)) },
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    focusedTextColor = Color.Black,
                    cursorColor = Color.Black
                ),
                visualTransformation = PasswordVisualTransformation()
            )
        }
        if (!isLogin && confirmPasswordEmptyError) {
            Text(
                modifier = Modifier.padding(0.dp),
                text = stringResource(R.string.error_message_password_valid)
                , fontSize = 10.sp, color = Color.Red)
        }
        if (!isLogin && confirmPasswordValidError && !confirmPasswordEmptyError) {
            Text(
                modifier = Modifier.padding(0.dp),
                text = stringResource(R.string.error_message_password_valid),
                fontSize = 10.sp,
                color = Color.Red
            )
        }
        if (!isLogin && confirmDoublePasswordError && !confirmPasswordValidError && !confirmPasswordEmptyError) {
            Text(
                modifier = Modifier.padding(0.dp),
                text = stringResource(R.string.error_message_password_match),
                fontSize = 10.sp,
                color = Color.Red
            )

        }
        Text(
            modifier = Modifier.clickable { isLogin = !isLogin },
            text = if (isLogin) stringResource(R.string.title_navigate_to_register) else stringResource(
                R.string.title_navigate_to_login
            ),
            color = Color.Gray,
            fontSize = 14.sp
        )
        Button(
            modifier = Modifier
                .padding(top = 16.dp)
                .size(210.dp, 60.dp),
            shape = RectangleShape,
            onClick = {
                emailEmptyError = emailValue.isEmpty()
                emailValidError =
                    !(emailValue.contains("@") && emailValue.substringAfter("@").isNotEmpty())
                passwordEmptyError = passwordValue.isEmpty()
                passwordValidError = passwordValue.length < 8
                if (!isLogin) {
                    confirmPasswordEmptyError = confirmPasswordValue.isEmpty()
                    confirmPasswordValidError = confirmPasswordValue.length < 8
                    confirmDoublePasswordError = passwordValue != confirmPasswordValue

                }
                if (isLogin) {
                    if (!emailEmptyError && !emailValidError && !passwordEmptyError && !passwordValidError) {
                        authViewModel.startAuth(emailValue, passwordValue, isLogin = isLogin)
                    }
                } else {
                    if (!emailEmptyError && !emailValidError && !passwordEmptyError && !passwordValidError && !confirmPasswordValidError && !confirmPasswordEmptyError && !confirmDoublePasswordError) {
                        authViewModel.startAuth(emailValue, passwordValue, confirmPasswordValue, isLogin = isLogin)
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
        ) {
            Text(text = if (isLogin) stringResource(R.string.title_login) else stringResource(R.string.title_navigate_to_register), color = Color.White)
        }
        if (state.viewMessage) {
            Text(
                modifier = Modifier.padding(0.dp),
                text = state.message,
                fontSize = 10.sp,
                color = Color.Red
            )
        }
    }
}