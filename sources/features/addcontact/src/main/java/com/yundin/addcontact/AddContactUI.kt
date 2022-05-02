package com.yundin.addcontact

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yundin.addcontact.di.DaggerAddContactComponent
import com.yundin.core.App
import com.yundin.core.utils.daggerViewModel
import com.yundin.designsystem.AddContactButton

@Composable
fun AddContactScreen(
    showShackbar: suspend (String) -> Unit
) {
    val app = LocalContext.current.applicationContext as App
    val viewModel: AddContactViewModel = viewModel(
        factory = daggerViewModel {
            DaggerAddContactComponent.builder()
                .addContactDependencies(app.getAppProvider())
                .build()
                .viewModel
        }
    )
    val snackbarText by viewModel.snackbarText.observeAsState()
    LaunchedEffect(snackbarText) {
        if (snackbarText != null) {
            showShackbar(snackbarText!!)
            viewModel.onSnackbarShown()
        }
    }
    val name by viewModel.newContactName.observeAsState()
    AddContactScreenContent(
        name = name,
        onNameChange = { viewModel.onNameChange(it) },
        onAddClick = { viewModel.onAddContactClick() }
    )
}

@Composable
fun AddContactScreenContent(
    name: String?,
    onNameChange: (String) -> Unit,
    onAddClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = name.orEmpty(),
            onValueChange = onNameChange,
            modifier = Modifier.fillMaxWidth()
        )
        AddContactButton(
            contactName = name,
            onClick = onAddClick
        )
    }
}
