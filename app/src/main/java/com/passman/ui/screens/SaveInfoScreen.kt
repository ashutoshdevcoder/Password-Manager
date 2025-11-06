package com.passman.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.passman.R
import com.passman.models.CardInfo
import com.passman.models.PasswordInfo
import com.passman.ui.customView.CustomAppBar
import com.passman.ui.customView.CustomTextView
import com.passman.ui.customView.OutlineRoundedButton
import com.passman.ui.theme.darkBlue
import com.passman.ui.theme.regular
import com.passman.ui.theme.semiBold
import com.passman.viewModel.SaveInfoEvent
import com.passman.viewModel.SaveInfoViewModel

@Composable
fun SaveInfoScreen(
    isAddCard: Boolean,
    id: Int?,
    saveInfoViewModel: SaveInfoViewModel?,
    onClicked: (Screens?) -> Unit
) {

    val getCardInfoState = saveInfoViewModel?.getCardInfoState?.collectAsStateWithLifecycle()
    val getPasswordInfoState =
        saveInfoViewModel?.getPasswordInfoState?.collectAsStateWithLifecycle()

    val account = remember { mutableStateOf("") }
    val username = remember { mutableStateOf("") }
    val loginPassword = remember { mutableStateOf("") }
    val profilePassword = remember { mutableStateOf("") }
    val website = remember { mutableStateOf("") }
    val notes = remember { mutableStateOf("") }

    val bankname = remember { mutableStateOf("") }
    val cardNumber = remember { mutableStateOf("") }
    val expiryDate = remember { mutableStateOf("") }
    val cvvNumber = remember { mutableStateOf("") }
    val atmPin = remember { mutableStateOf("") }
    val cardNotes = remember { mutableStateOf("") }
    var isEditing by remember { mutableStateOf(true) }


    if (id != null) {
        LaunchedEffect(Unit) {
            isEditing =false
            if (isAddCard)
                saveInfoViewModel?.onEvent(SaveInfoEvent.GetCardInfoEvent(id))
            else
                saveInfoViewModel?.onEvent(SaveInfoEvent.GetPasswordInfoEvent(id))
        }

    }
    LaunchedEffect(getPasswordInfoState?.value) {
        account.value = getPasswordInfoState?.value?.account.orEmpty()
        username.value = getPasswordInfoState?.value?.username.orEmpty()
        loginPassword.value = getPasswordInfoState?.value?.loginPassword.orEmpty()
        profilePassword.value = getPasswordInfoState?.value?.profilePassword.orEmpty()
        website.value = getPasswordInfoState?.value?.website.orEmpty()
        notes.value = getPasswordInfoState?.value?.notes.orEmpty()
    }

    LaunchedEffect(getCardInfoState?.value) {
        bankname.value = getCardInfoState?.value?.bankName.orEmpty()
        cardNumber.value = getCardInfoState?.value?.cardNumber.orEmpty()
        expiryDate.value = getCardInfoState?.value?.expiryDate.orEmpty()
        cvvNumber.value = getCardInfoState?.value?.cvvNo.orEmpty()
        atmPin.value = getCardInfoState?.value?.pin.orEmpty()
        cardNotes.value = getCardInfoState?.value?.notes.orEmpty()
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        CustomAppBar(
            action = {
                if(id!=null)
                {
                    IconButton(
                        onClick = {
                            isEditing = !isEditing
                        }
                    ) {
                        Icon(
                            imageVector = if (isEditing) Icons.Filled.Done else Icons.Filled.Edit,
                            contentDescription = if (isEditing) "Done" else "Edit"
                        )
                    }
                }
            },
            containerColor = Color.White,
            titleContentColor = darkBlue,
            navigationIconColor = darkBlue,
            onBackButtonClick = {
                onClicked(null)
            }, title = "Add Information"
        )
        Box {
            if (isAddCard) {
                Column(modifier = Modifier.padding(16.dp).imePadding().verticalScroll(
                    rememberScrollState()
                )) {
                    OutlinedTextField(
                        enabled = isEditing,
                        value = bankname.value,
                        onValueChange = { bankname.value = it },
                        label = {
                            CustomTextView(
                                stringResource(R.string.bank_name),
                                fontFamily = regular
                            )
                        },
                        placeholder = {
                            CustomTextView(
                                stringResource(R.string.canara_bank),
                                fontFamily = regular,
                                color = colorResource(R.color.colorGreyE0)
                            )
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth(),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = darkBlue,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                            disabledTextColor = darkBlue


                        )

                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    OutlinedTextField(
                        enabled = isEditing,
                        value = cardNumber.value,
                        onValueChange = { cardNumber.value = it },
                        label = {
                            CustomTextView(
                                stringResource(R.string.card_number),
                                fontFamily = regular
                            )
                        },
                        placeholder = {
                            CustomTextView(
                                stringResource(R.string.card_ex),
                                fontFamily = regular,
                                color = colorResource(R.color.colorGreyE0)
                            )
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth(),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = darkBlue,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                            disabledTextColor = darkBlue

                        )

                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    OutlinedTextField(
                        enabled = isEditing,
                        value = expiryDate.value,
                        onValueChange = { expiryDate.value = it },
                        label = {
                            CustomTextView(
                                stringResource(R.string.expiry_date),
                                fontFamily = regular
                            )
                        },
                        placeholder = {
                            CustomTextView(
                                stringResource(R.string.expiry_ex),
                                fontFamily = regular,
                                color = colorResource(R.color.colorGreyE0)
                            )
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth(),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = darkBlue,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                            disabledTextColor = darkBlue

                        )

                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    OutlinedTextField(
                        enabled = isEditing,
                        value = cvvNumber.value,
                        onValueChange = { cvvNumber.value = it },
                        label = {
                            CustomTextView(
                                stringResource(R.string.cvv_number),
                                fontFamily = regular
                            )
                        },
                        placeholder = {
                            CustomTextView(
                                stringResource(R.string.cvv_ex),
                                fontFamily = regular,
                                color = colorResource(R.color.colorGreyE0)
                            )
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth(),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = darkBlue,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                            disabledTextColor = darkBlue

                        )

                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    OutlinedTextField(
                        enabled = isEditing,
                        value = atmPin.value,
                        onValueChange = { atmPin.value = it },
                        label = {
                            CustomTextView(
                                stringResource(R.string.pin),
                                fontFamily = regular
                            )
                        },
                        placeholder = {
                            CustomTextView(
                                stringResource(R.string.pin_ex),
                                fontFamily = regular
                            )
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth(),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = darkBlue,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                            disabledTextColor = darkBlue

                        )

                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    OutlinedTextField(
                        enabled = isEditing,
                        value = cardNotes.value,
                        onValueChange = { cardNotes.value = it },
                        label = {
                            CustomTextView(
                                stringResource(R.string.notes),
                                fontFamily = regular
                            )
                        },
                        placeholder = {
                            CustomTextView(
                                stringResource(R.string.this_id_is_new),
                                fontFamily = regular,
                                color = colorResource(R.color.colorGreyE0)
                            )
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth(),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = darkBlue,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                            disabledTextColor = darkBlue

                        )

                    )
                    Spacer(modifier = Modifier.size(30.dp))

                    OutlineRoundedButton(
                        onClick = {
                            if (bankname.value.isEmpty()) {
                                return@OutlineRoundedButton
                            } else {
                                val cardInfo = CardInfo(
                                    bankName = bankname.value,
                                    cardNumber = cardNumber.value,
                                    expiryDate = expiryDate.value,
                                    cvvNo = cvvNumber.value,
                                    pin = atmPin.value,
                                    notes = cardNotes.value,
                                )
                                saveInfoViewModel?.onEvent(
                                    SaveInfoEvent.SaveCardInfoEvent(
                                        cardInfo
                                    )
                                )
                                onClicked(null)
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        backgroundColor = colorResource(R.color.colorPrimary),
                        textColor = Color.White,
                        text = stringResource(R.string.save),
                        fontSize = 14.sp,
                        fontFamily = semiBold // Replace with your actual font
                    )
                }
            } else {
                Column(modifier = Modifier.padding(16.dp).imePadding().verticalScroll(
                    rememberScrollState()
                )) {
                    OutlinedTextField(
                        enabled = isEditing,
                        value = account.value,
                        onValueChange = { account.value = it },
                        label = {
                            CustomTextView(
                                stringResource(R.string.account),
                                fontFamily = regular
                            )
                        },
                        placeholder = {
                            CustomTextView(
                                stringResource(R.string.facebook),
                                fontFamily = regular,
                                color = colorResource(R.color.colorGreyE0)
                            )
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth(),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = darkBlue,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                            disabledTextColor = darkBlue

                        )

                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    OutlinedTextField(
                        enabled = isEditing,
                        value = username.value,
                        onValueChange = { username.value = it },
                        label = {
                            CustomTextView(
                                stringResource(R.string.username),
                                fontFamily = regular
                            )
                        },
                        placeholder = {
                            CustomTextView(
                                stringResource(R.string.abc_123),
                                fontFamily = regular,
                                color = colorResource(R.color.colorGreyE0)
                            )
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth(),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,   imeAction = ImeAction.Next
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = darkBlue,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                            disabledTextColor = darkBlue

                        )

                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    OutlinedTextField(
                        enabled = isEditing,
                        value = loginPassword.value,
                        onValueChange = { loginPassword.value = it },
                        label = {
                            CustomTextView(
                                stringResource(R.string.login_password),
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
                            keyboardType = KeyboardType.Text,   imeAction = ImeAction.Next
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = darkBlue,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                            disabledTextColor = darkBlue

                        )

                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    OutlinedTextField(
                        enabled = isEditing,
                        value = profilePassword.value,
                        onValueChange = { profilePassword.value = it },
                        label = {
                            CustomTextView(
                                stringResource(R.string.profile_password),
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
                            keyboardType = KeyboardType.Text,  imeAction = ImeAction.Next
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = darkBlue,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                            disabledTextColor = darkBlue

                        )

                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    OutlinedTextField(
                        enabled = isEditing,
                        value = website.value,
                        onValueChange = { website.value = it },
                        label = {
                            CustomTextView(
                                stringResource(R.string.website_url),
                                fontFamily = regular
                            )
                        },
                        placeholder = {
                            CustomTextView(
                                stringResource(R.string.facebook_ex),
                                fontFamily = regular,
                                color = colorResource(R.color.colorGreyE0)
                            )
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth(),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = darkBlue,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                            disabledTextColor = darkBlue

                        )

                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    OutlinedTextField(
                        enabled = isEditing,
                        value = notes.value,
                        onValueChange = { notes.value = it },
                        label = {
                            CustomTextView(
                                stringResource(R.string.notes),
                                fontFamily = regular
                            )
                        },
                        placeholder = {
                            CustomTextView(
                                stringResource(R.string.this_id_is_new),
                                fontFamily = regular,
                                color = colorResource(R.color.colorGreyE0)
                            )
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth(),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = darkBlue,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                            disabledTextColor = darkBlue

                        )

                    )
                    Spacer(modifier = Modifier.size(30.dp))

                    OutlineRoundedButton(
                        onClick = {
                            if (account.value.isEmpty()) {
                                return@OutlineRoundedButton
                            } else {
                                val passwordInfo = PasswordInfo(
                                    account = account.value,
                                    username = username.value,
                                    loginPassword = loginPassword.value,
                                    profilePassword = profilePassword.value,
                                    website = website.value,
                                    notes = notes.value,
                                )
                                saveInfoViewModel?.onEvent(
                                    SaveInfoEvent.SavePasswordInfoEvent(
                                        passwordInfo
                                    )
                                )
                                onClicked(null)
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        backgroundColor = colorResource(R.color.colorPrimary),
                        textColor = Color.White,
                        text = stringResource(R.string.save),
                        fontSize = 14.sp,
                        fontFamily = semiBold // Replace with your actual font
                    )
                }
            }
        }

    }
}


@Composable
@Preview
fun SaveInfoScreenPreview() {
    SaveInfoScreen(false, null, null, {})
}