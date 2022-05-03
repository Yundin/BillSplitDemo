package com.yundin.addcontact

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yundin.core.repository.ContactsRepository
import com.yundin.core.utils.ResourceProvider
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddContactViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    private val _newContactName = MutableLiveData("")
    val newContactName: LiveData<String> = _newContactName
    private val _snackbarText = MutableLiveData<String?>(null)
    val snackbarText: LiveData<String?> = _snackbarText

    fun onNameChange(name: String) {
        _newContactName.value = name
    }

    fun onAddContactClick() {
        val name = _newContactName.value
        if (!name.isNullOrBlank()) {
            addContact(name)
        } else {
            _snackbarText.value = resourceProvider.getString(R.string.empty_name_error)
        }
    }

    fun onSnackbarShown() {
        _snackbarText.value = null
    }

    private fun addContact(name: String) {
        viewModelScope.launch {
            try {
                contactsRepository.addContact(name)
                _newContactName.value = ""
                _snackbarText.value =
                    resourceProvider.getString(R.string.contact_added_format, name)
            } catch (_: SQLiteConstraintException) {
                _snackbarText.value = resourceProvider.getString(R.string.contact_exists_error)
            }
        }
    }
}
