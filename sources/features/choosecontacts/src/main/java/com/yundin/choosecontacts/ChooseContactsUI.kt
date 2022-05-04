package com.yundin.choosecontacts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.yundin.choosecontacts.di.DaggerChooseContactsComponent
import com.yundin.core.App
import com.yundin.core.utils.daggerViewModelFactory
import com.yundin.designsystem.AddContactButton
import com.yundin.designsystem.CheckableContactItem
import com.yundin.navigation.Screen
import kotlinx.coroutines.launch

@Composable
fun ChooseContactsScreen(
    navController: NavController,
    showSnackbar: (String) -> Unit,
    selectedIds: LongArray?
) {
    val app = LocalContext.current.applicationContext as App
    val viewModel: ChooseContactsViewModel = viewModel(
        factory = daggerViewModelFactory(
            dependencies = app.getAppProvider(),
            builder = DaggerChooseContactsComponent.builder()
        )
    )
    LaunchedEffect(selectedIds) {
        if (selectedIds != null) {
            viewModel.setSelectedIds(selectedIds)
        }
    }
    val snackbarText by viewModel.snackbarText.observeAsState()
    LaunchedEffect(snackbarText) {
        if (snackbarText != null) {
            showSnackbar(snackbarText!!)
            viewModel.onSnackbarShown()
        }
    }
    val name by viewModel.inputName.observeAsState()
    val contacts by viewModel.contactList.observeAsState(listOf())
    val coroutineScope = rememberCoroutineScope()
    val onDoneClick: () -> Unit = {
        coroutineScope.launch {
            val selectedContacts = viewModel.getSelectedContacts()
            navController.previousBackStackEntry
                ?.savedStateHandle
                ?.set(Screen.ChooseContacts.route, selectedContacts)
            navController.popBackStack()
        }
    }
    ChooseContactsScreenContent(
        name = name,
        onNameChange = viewModel::onNameChange,
        onAddClick = viewModel::onAddContactClick,
        contacts = contacts,
        onContactClick = { viewModel.onContactClick(it.id) },
        onDoneClick = onDoneClick
    )
}

@Composable
private fun ChooseContactsScreenContent(
    name: String?,
    onNameChange: (String) -> Unit,
    onAddClick: () -> Unit,
    contacts: List<UiContact>,
    onContactClick: (UiContact) -> Unit,
    onDoneClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        TopAppBar {
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = "Choose contacts")
            Spacer(modifier = Modifier.weight(1f))
            TextButton(onClick = onDoneClick) {
                Text(
                    text = "Done",
                    color = MaterialTheme.colors.secondary
                )
            }
        }
        TextField(
            value = name.orEmpty(),
            onValueChange = onNameChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        AddContactButton(
            contactName = name,
            onClick = onAddClick
        )
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(contacts) { contact ->
                CheckableContactItem(
                    name = contact.name,
                    checked = contact.checked,
                    modifier = Modifier.clickable {
                        onContactClick(contact)
                    }
                )
            }
        }
    }
}
