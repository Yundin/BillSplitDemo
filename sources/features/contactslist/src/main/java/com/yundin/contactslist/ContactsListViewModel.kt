package com.yundin.contactslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yundin.core.model.Contact
import com.yundin.core.repository.ContactsRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class ContactsListViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository
) : ViewModel() {
    private val _contacts: MutableLiveData<List<Contact>> = MutableLiveData()
    val contacts: LiveData<List<Contact>> = _contacts

    init {
        viewModelScope.launch {
            contactsRepository.contacts.collect {
                _contacts.value = it
            }
        }
    }
}