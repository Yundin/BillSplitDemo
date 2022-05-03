package com.yundin.addgroup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.yundin.addgroup.di.DaggerAddGroupComponent
import com.yundin.core.App
import com.yundin.core.model.Contact
import com.yundin.core.utils.daggerViewModelFactory
import com.yundin.designsystem.ContactItem
import com.yundin.navigation.Screen

@Composable
fun AddGroupScreen(
    navController: NavController,
    showSnackbar: (String) -> Unit,
) {
    val app = LocalContext.current.applicationContext as App
    val viewModel: AddGroupViewModel = viewModel(
        factory = daggerViewModelFactory {
            DaggerAddGroupComponent.builder()
                .addGroupDependencies(app.getAppProvider())
                .build()
                .viewModel
        }
    )
    val uiState by viewModel.uiState.observeAsState(AddGroupViewModel.UiState())
    LaunchedEffect(uiState.snackbarText) {
        uiState.snackbarText?.let {
            showSnackbar(it)
            viewModel.onSnackbarShown()
        }
    }
    LaunchedEffect(uiState.screenFinished) {
        if (uiState.screenFinished) {
            navController.popBackStack()
        }
    }
    val chooseContactsResult = navController.currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<List<Contact>>(Screen.ChooseContacts.route)
        ?.observeAsState()
    LaunchedEffect(chooseContactsResult) {
        viewModel.onContactsAdded(chooseContactsResult?.value)
    }
    AddGroupScreenContent(
        groupTitle = uiState.groupTitle.text,
        titleError = uiState.groupTitle.errorText,
        onTitleChange = viewModel::onTitleChange,
        checkAmount = uiState.checkAmount.text,
        amountError = uiState.checkAmount.errorText,
        onAmountChange = viewModel::onAmountChange,
        contacts = uiState.contacts,
        onAddGroupClick = viewModel::onAddGroupClick,
        onAddParticipantsClick = {
            navController.navigate(Screen.ChooseContacts.route)
        }
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun AddGroupScreenContent(
    groupTitle: String?,
    titleError: String?,
    onTitleChange: (String) -> Unit,
    checkAmount: String?,
    amountError: String?,
    onAmountChange: (String) -> Unit,
    contacts: List<Contact>?,
    onAddParticipantsClick: () -> Unit,
    onAddGroupClick: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        TextField(
            label = {
                val label = titleError ?: stringResource(R.string.group_title_hint)
                Text(text = label)
            },
            isError = titleError != null,
            value = groupTitle.orEmpty(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            onValueChange = onTitleChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        )
        val keyboardController = LocalSoftwareKeyboardController.current
        TextField(
            label = {
                val label = amountError ?: stringResource(R.string.check_amount_hint)
                Text(text = label)
            },
            isError = amountError != null,
            value = checkAmount.orEmpty(),
            onValueChange = onAmountChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            )
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onAddParticipantsClick)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Filled.AddCircle, null)
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = stringResource(R.string.add_group_participants_btn))
        }
        if (!contacts.isNullOrEmpty()) {
            for (contact in contacts) {
                ContactItem(name = contact.name, debt = null)
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = onAddGroupClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = stringResource(R.string.create_group_btn))
        }
    }
}
