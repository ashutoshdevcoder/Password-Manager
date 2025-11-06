package com.passman.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.passman.R
import com.passman.helper.isEqualExt
import com.passman.helper.isFalse
import com.passman.helper.isTrue
import com.passman.helper.showToast
import com.passman.models.UserInfo
import com.passman.ui.customView.CustomTextView
import com.passman.ui.customView.OutlineRoundedButton
import com.passman.ui.theme.darkBlue
import com.passman.ui.theme.regular
import com.passman.ui.theme.semiBold
import com.passman.viewModel.LoginEvent
import com.passman.viewModel.LoginViewModel

@Composable
fun LoginScreen(loginViewModel: LoginViewModel?,onClicked: (Screens?) -> Unit){

    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val minLength = 4 // Minimum required characters
    val isValid = password.length >= minLength
    val isUserRegisteredState = loginViewModel?.isUserRegisteredState?.collectAsStateWithLifecycle()
    val isUserRegistered = remember { derivedStateOf { isUserRegisteredState?.value  } }
    val isUserLoggedInState = loginViewModel?.isUserExist?.collectAsStateWithLifecycle()
    val isUserLoggedIn= remember { derivedStateOf { isUserLoggedInState?.value } }
    val context = LocalContext.current

    LaunchedEffect(isUserLoggedIn.value) {
        if(isUserLoggedIn.value.isTrue())
        {
            onClicked(Screens.HomeScreens)
        }
        else
        {
            if(isUserLoggedIn.value!=null)
                context.showToast(context.getString(R.string.entered_password_is_incorrect))
        }
    }


    Box(modifier = Modifier.background(Color.White)) {
        Column (
            modifier = Modifier
                .padding(horizontal = 30.dp)
                .background(color = Color.White)
                .imePadding()
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Lock,
                contentDescription = "Login",
                modifier = Modifier.size(64.dp),
                tint = darkBlue
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomTextView(
                text = stringResource(R.string.welcome_back),
                fontFamily = semiBold,
                color = darkBlue,
                fontSize = 28.sp
            )
            Spacer(modifier = Modifier.height(22.dp))
            if(isUserRegistered.value.isTrue())
            CustomTextView(
                text = stringResource(R.string.login_to_your_account),
                fontFamily = regular,
                color = darkBlue,
                fontSize = 20.sp
            )
            else
            {
                CustomTextView(
                    text = stringResource(R.string.please_register),
                    fontFamily = regular,
                    color = colorResource(R.color.colorGrey42),
                    fontSize = 16.sp,
                )
            }
            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = {
                    CustomTextView(
                        stringResource(R.string.password),
                        fontFamily = regular
                    )
                },
                placeholder = {
                    CustomTextView(
                        stringResource(R.string.pass_ex),
                        fontFamily = regular,
                        color = colorResource(R.color.colorGreyE0)
                    )
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                supportingText = {
                    if (!isValid && password.isNotEmpty()) {
                        CustomTextView("Minimum $minLength characters required", fontFamily = regular, color = Color.Red)
                    }
                },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = darkBlue,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    disabledTextColor = darkBlue

                ),visualTransformation = if (passwordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                leadingIcon = {
                    Icon(Icons.Filled.Lock, contentDescription = "Password")
                },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painter = if (passwordVisible) painterResource(R.drawable.visible_pass)
                            else painterResource(R.drawable.invisible_pass),
                            contentDescription = if (passwordVisible) "Hide password"
                            else "Show password"
                        )
                    }
                }


            )
            if(isUserRegistered.value.isFalse()) {
                Spacer(modifier = Modifier.height(32.dp))

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = {
                        CustomTextView(
                            stringResource(R.string.confirm_password),
                            fontFamily = regular
                        )
                    },
                    placeholder = {
                        CustomTextView(
                            stringResource(R.string.pass_ex),
                            fontFamily = regular,
                            color = colorResource(R.color.colorGreyE0)
                        )
                    },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = darkBlue,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        disabledTextColor = darkBlue

                    ),
                    leadingIcon = {
                        Icon(Icons.Filled.Lock, contentDescription = "Password")
                    },
                )
            }
            Spacer(modifier = Modifier.height(32.dp))

            OutlineRoundedButton(
                onClick = {
                    if(password.isEmpty())
                    {
                        context.showToast(context.getString(R.string.please_enter_password))
                        return@OutlineRoundedButton
                    }
                    if(isValid.not())
                    {
                        context.showToast(context.getString(R.string.please_enter_valid_password))
                        return@OutlineRoundedButton
                    }

                    if(isUserRegistered.value.isFalse())
                    {
                        if(password.isEqualExt(confirmPassword))
                            loginViewModel?.onEvent(LoginEvent.RegisterUserEvent(UserInfo(password =  password)))
                        else
                            context.showToast(context.getString(R.string.password_and_confirm_password_should_be_same))

                    }
                    else
                        loginViewModel?.onEvent(LoginEvent.LoginUserEvent(password))

                },
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = colorResource(R.color.colorPrimary),
                textColor = Color.White,
                text = if(isUserRegistered.value.isFalse()) stringResource(R.string.save) else stringResource(R.string.login),
                fontSize = 14.sp,
                fontFamily = semiBold // Replace with your actual font
            )

        }
    }
}

@Composable
@Preview
fun LoginScreenPreview(){
    LoginScreen(null,{})
}