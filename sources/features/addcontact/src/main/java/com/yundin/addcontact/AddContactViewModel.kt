package com.yundin.addcontact

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yundin.core.repository.ContactsRepository
import com.yundin.core.utils.NativeText
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddContactViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository,
) : ViewModel() {

    private val _newContactName = MutableLiveData("")
    val newContactName: LiveData<String> = _newContactName
    private val _snackbarText = MutableLiveData<NativeText?>(null)
    val snackbarText: LiveData<NativeText?> = _snackbarText

    fun onNameChange(name: String) {
        _newContactName.value = name
    }

    fun onAddContactClick() {
        val name = _newContactName.value
        if (!name.isNullOrBlank()) {
            addContact(name)
        } else {
            _snackbarText.value = NativeText.Resource(R.string.empty_name_error)
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
                    NativeText.Arguments(R.string.contact_added_format, listOf(name))
            } catch (_: SQLiteConstraintException) {
                _snackbarText.value = NativeText.Resource(R.string.contact_exists_error)
            }
        }
    }
}
