package com.yundin.grouplist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yundin.core.model.Group
import com.yundin.core.repository.GroupsRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class GroupListViewModel @Inject constructor(
    private val groupsRepository: GroupsRepository
) : ViewModel() {
    private val _groups: MutableLiveData<List<Group>> = MutableLiveData()
    val groups: LiveData<List<Group>> = _groups

    init {
        viewModelScope.launch {
            groupsRepository.groups.collect {
                _groups.value = it
            }
        }
    }
}