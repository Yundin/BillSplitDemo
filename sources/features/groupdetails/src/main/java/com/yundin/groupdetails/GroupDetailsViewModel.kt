package com.yundin.groupdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yundin.core.model.Group
import com.yundin.core.model.GroupContact
import com.yundin.core.repository.GroupsRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class GroupDetailsViewModel @Inject constructor(
    private val groupsRepository: GroupsRepository,
) : ViewModel() {

    private val _uiGroup = MutableLiveData<Group>()
    val uiGroup: LiveData<Group> = _uiGroup

    fun setId(groupId: Long) {
        viewModelScope.launch {
            groupsRepository.getById(groupId).collect { group ->
                _uiGroup.value = group.copy(
                    contacts = group.contacts.sortedBy { it.checked }
                )
            }
        }
    }

    fun onContactClick(contact: GroupContact) {
        viewModelScope.launch {
            groupsRepository.setContactChecked(
                groupId = checkNotNull(uiGroup.value).id,
                contactId = contact.id,
                checked = !contact.checked
            )
        }
    }
}
