package com.yundin.addcontact

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yundin.addcontact.di.DaggerAddContactComponent
import com.yundin.core.App
import com.yundin.core.utils.daggerViewModelFactory
import com.yundin.core.utils.toCharSequence
import com.yundin.designsystem.AddContactButton

@Composable
fun AddContactScreen(
    showSnackbar: (String) -> Unit
) {
    val app = LocalContext.current.applicationContext as App
    val viewModel: AddContactViewModel = viewModel(
        factory = daggerViewModelFactory(
            dependencies = app.getAppProvider(),
            builder = DaggerAddContactComponent.builder()
        )
    )
    val context = LocalContext.current
    val snackbarText by viewModel.snackbarText.observeAsState()
    LaunchedEffect(snackbarText) {
        snackbarText?.let {
            showSnackbar(it.toCharSequence(context).toString())
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
private fun AddContactScreenContent(
    name: String?,
    onNameChange: (String) -> Unit,
    onAddClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = name.orEmpty(),
            onValueChange = onNameChange,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words
            ),
            singleLine = true
        )
        AddContactButton(
            contactName = name,
            onClick = onAddClick
        )
    }
}
