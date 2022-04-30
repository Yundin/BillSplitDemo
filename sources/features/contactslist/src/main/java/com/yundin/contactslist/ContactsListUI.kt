package com.yundin.contactslist

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yundin.contactslist.di.DaggerContactsListComponent
import com.yundin.core.App
import com.yundin.core.model.Contact
import com.yundin.core.utils.daggerViewModel
import com.yundin.designsystem.ContactItem

@Composable
fun ContactsListScreen() {
    val app = LocalContext.current.applicationContext as App
    val viewModel: ContactsListViewModel = viewModel(
        factory = daggerViewModel {
            DaggerContactsListComponent.builder()
                .contactsListDependencies(app.getAppProvider())
                .build()
                .viewModel
        }
    )
    val contacts: List<Contact>? by viewModel.contacts.observeAsState()
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(contacts.orEmpty()) { contact ->
            ContactItem(name = contact.name, debt = contact.owesOverall)
        }
    }
}
