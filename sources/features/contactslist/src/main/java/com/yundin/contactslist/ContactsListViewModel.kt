package com.yundin.contactslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yundin.core.model.Contact
import com.yundin.core.repository.ContactsRepository
import com.yundin.core.utils.NativeText
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class ContactsListViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository,
) : ViewModel() {

    private val _contacts: MutableLiveData<List<Contact>> = MutableLiveData()
    val contacts: LiveData<List<Contact>> = _contacts
    private val _snackbarText = MutableLiveData<NativeText?>(null)
    val snackbarText: LiveData<NativeText?> = _snackbarText

    init {
        viewModelScope.launch {
            contactsRepository.observeContacts().collect {
                _contacts.value = it
            }
        }
    }

    fun onContactRemoveClick(contact: Contact) {
        viewModelScope.launch {
            val isRemoved = contactsRepository.removeContact(contact.id)
            if (!isRemoved) {
                _snackbarText.value =
                    NativeText.Resource(R.string.contact_cannot_be_deleted)
            }
        }
    }

    fun onSnackbarShown() {
        _snackbarText.value = null
    }
}
