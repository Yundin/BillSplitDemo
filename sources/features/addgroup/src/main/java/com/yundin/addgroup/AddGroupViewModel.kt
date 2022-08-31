package com.yundin.addgroup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yundin.core.model.Contact
import com.yundin.core.repository.GroupsRepository
import com.yundin.core.utils.NativeText
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

class AddGroupViewModel @Inject constructor(
    private val groupRepository: GroupsRepository,
) : ViewModel() {

    private val _uiState = MutableLiveData(UiState())
    private val currentUiState
        get() = checkNotNull(_uiState.value)
    val uiState: LiveData<UiState> = _uiState

    fun onTitleChange(title: String) {
        _uiState.value = currentUiState.copy(
            groupTitle = FieldState(title)
        )
    }

    fun onAmountChange(amount: String) {
        val dotAmount = amount.replace(',', '.')
        if (checkAmountValid(dotAmount)) {
            _uiState.value = currentUiState.copy(
                checkAmount = FieldState(amount)
            )
        }
    }

    fun onAddGroupClick() {
        if (validateWithErrors()) {
            viewModelScope.launch {
                val addedGroup = groupRepository.addGroup(
                    title = currentUiState.groupTitle.text,
                    sum = BigDecimal(currentUiState.checkAmount.text),
                    contactIds = currentUiState.contacts.map { it.id }
                )
                _uiState.value = currentUiState.copy(
                    snackbarText = NativeText.Resource(R.string.group_added),
                    createdGroupId = addedGroup.id
                )
            }
        }
    }

    fun onRemoveContactClick(contact: Contact) {
        _uiState.value = currentUiState.copy(
            contacts = currentUiState.contacts
                .toMutableList()
                .apply {
                    remove(contact)
                }
        )
    }

    fun onSnackbarShown() {
        _uiState.value = currentUiState.copy(
            snackbarText = null
        )
    }

    fun onContactsAdded(contacts: List<Contact>?) {
        if (currentUiState.contacts != contacts && contacts != null) {
            _uiState.value = currentUiState.copy(
                contacts = contacts
            )
        }
    }

    private fun validateWithErrors(): Boolean {
        var allValid = true
        if (!checkTitleValid(currentUiState.groupTitle.text)) {
            _uiState.value = currentUiState.copy(
                groupTitle = currentUiState.groupTitle.copy(
                    errorText = NativeText.Resource(R.string.group_title_validation_error)
                )
            )
            allValid = false
        }
        if (!checkAmountValid(currentUiState.checkAmount.text)) {
            _uiState.value = currentUiState.copy(
                checkAmount = currentUiState.checkAmount.copy(
                    errorText = NativeText.Resource(R.string.check_amount_validation_error)
                )
            )
            allValid = false
        }
        if (currentUiState.contacts.isEmpty()) {
            _uiState.value = currentUiState.copy(
                snackbarText = NativeText.Resource(
                    R.string.no_participants_validation_error
                ),
            )
            allValid = false
        }
        return allValid
    }

    private fun checkTitleValid(title: String): Boolean {
        return title.isNotBlank()
    }

    private fun checkAmountValid(amount: String?): Boolean {
        return try {
            val bigDecimal = BigDecimal(amount)
            bigDecimal.scale() <= 2 && bigDecimal > BigDecimal.ZERO
        } catch (_: NumberFormatException) {
            false
        }
    }

    data class UiState(
        val groupTitle: FieldState = FieldState(),
        val checkAmount: FieldState = FieldState(),
        val contacts: List<Contact> = listOf(),
        val snackbarText: NativeText? = null,
        val createdGroupId: Long? = null
    )
}

