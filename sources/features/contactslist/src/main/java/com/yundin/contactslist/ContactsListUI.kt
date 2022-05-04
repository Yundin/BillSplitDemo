package com.yundin.contactslist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yundin.contactslist.di.DaggerContactsListComponent
import com.yundin.core.App
import com.yundin.core.model.Contact
import com.yundin.core.utils.daggerViewModelFactory
import com.yundin.designsystem.ContactItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContactsListScreen(
    showSnackbar: (String) -> Unit
) {
    val app = LocalContext.current.applicationContext as App
    val viewModel: ContactsListViewModel = viewModel(
        factory = daggerViewModelFactory {
            DaggerContactsListComponent.builder()
                .contactsListDependencies(app.getAppProvider())
                .build()
                .viewModel
        }
    )
    val snackbarText by viewModel.snackbarText.observeAsState()
    LaunchedEffect(snackbarText) {
        if (snackbarText != null) {
            showSnackbar(snackbarText!!)
            viewModel.onSnackbarShown()
        }
    }
    val contacts: List<Contact>? by viewModel.contacts.observeAsState()
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(
            items = contacts.orEmpty(),
            key = { it.id }
        ) { contact ->
            ContactItem(
                name = contact.name,
                debt = null,
                onRemoveClick = { viewModel.onContactRemoveClick(contact) },
                modifier = Modifier.animateItemPlacement()
            )
        }
    }
    if (contacts.isNullOrEmpty()) {
        Text(
            textAlign = TextAlign.Center,
            text = stringResource(R.string.no_contacts),
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight()
        )
    }
}
