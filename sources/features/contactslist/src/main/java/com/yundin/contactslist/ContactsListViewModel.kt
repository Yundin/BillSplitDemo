package com.yundin.contactslist

import androidx.lifecycle.*
import com.yundin.core.model.Contact
import com.yundin.core.repository.ContactsRepository
import com.yundin.core.utils.NativeText
import kotlinx.coroutines.launch
import javax.inject.Inject

class ContactsListViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository,
) : ViewModel() {

    val contacts: LiveData<List<Contact>> = contactsRepository.observeContacts()
        .asLiveData(viewModelScope.coroutineContext)
    private val _snackbarText = MutableLiveData<NativeText?>(null)
    val snackbarText: LiveData<NativeText?> = _snackbarText

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
