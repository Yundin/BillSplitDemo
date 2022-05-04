package com.yundin.choosecontacts

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.*
import com.yundin.core.model.Contact
import com.yundin.core.repository.ContactsRepository
import com.yundin.core.utils.ResourceProvider
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChooseContactsViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository,
    private val resourceProvider: ResourceProvider
) : ViewModel() {
    private val _inputName = MutableLiveData("")
    val inputName: LiveData<String> = _inputName
    private val _snackbarText = MutableLiveData<String?>(null)
    val snackbarText: LiveData<String?> = _snackbarText

    private val allContacts: Flow<List<Contact>> = contactsRepository.contacts
    private val selectedContactIds = MutableStateFlow(listOf<Long>())
    private val contactListFlow: Flow<List<UiContact>> =
        combine(
            allContacts,
            selectedContactIds,
            inputName.asFlow()
        ) { contacts, selectedIds, inputName ->
            return@combine contacts
                .filter {
                    val input = inputName.toLowerCase(Locale.current)
                    val contactName = it.name.toLowerCase(Locale.current)
                    contactName.contains(input)
                }
                .map {
                    UiContact(
                        domain = it,
                        selected = it.id in selectedIds
                    )
                }
        }
    private val _contactList = MutableLiveData(listOf<UiContact>())
    val contactList: LiveData<List<UiContact>> = _contactList

    init {
        viewModelScope.launch {
            contactListFlow.collect {
                _contactList.value = it
            }
        }
    }

    fun setSelectedIds(selected: LongArray) {
        Log.d("ASDSAD get", selected.joinToString())
        viewModelScope.launch { selectedContactIds.emit(selected.toList()) }
    }

    fun onNameChange(name: String) {
        _inputName.value = name
    }

    fun onContactClick(id: Long) {
        viewModelScope.launch {
            val current = selectedContactIds.value
            selectedContactIds.emit(
                if (id in current) {
                    current.toMutableList().apply {
                        remove(id)
                    }
                } else {
                    current.toMutableList().apply {
                        add(id)
                    }
                }
            )
        }
    }

    fun onAddContactClick() {
        val name = inputName.nonNullValue
        if (name.isNotBlank()) {
            addContact(name)
        } else {
            _snackbarText.value = resourceProvider.getString(R.string.empty_name_error)
        }
    }

    fun onSnackbarShown() {
        _snackbarText.value = null
    }

    suspend fun getSelectedContacts(): List<Contact> {
        return allContacts.combine(selectedContactIds) { contacts, selected ->
            contacts.filter { it.id in selected }
        }.first()
    }

    private val <T> LiveData<T>.nonNullValue: T
        get() = checkNotNull(value)

    private fun addContact(name: String) {
        viewModelScope.launch {
            try {
                val addedContact = contactsRepository.addContact(name)
                _inputName.value = ""
                _snackbarText.value = resourceProvider.getString(
                    R.string.contact_added_and_checked_format,
                    name
                )
                selectedContactIds.emit(
                    selectedContactIds.value.toMutableList().apply { add(addedContact.id) }
                )
            } catch (_: SQLiteConstraintException) {
                _snackbarText.value = resourceProvider.getString(R.string.contact_exists_error)
            }
        }
    }
}

data class UiContact(
    val id: Long,
    val name: String,
    val checked: Boolean
) {
    constructor(domain: Contact, selected: Boolean) : this(domain.id, domain.name, selected)
}
