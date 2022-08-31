package com.yundin.grouplist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yundin.core.model.Group
import com.yundin.core.repository.GroupsRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

class GroupListViewModel @Inject constructor(
    private val groupsRepository: GroupsRepository
) : ViewModel() {
    private val _groups: MutableLiveData<List<UiGroup>> = MutableLiveData()
    val groups: LiveData<List<UiGroup>> = _groups

    init {
        viewModelScope.launch {
            groupsRepository.observeGroups().collect { domainList ->
                _groups.value = domainList
                    .sortedBy { it.dateCreated }
                    .map { domainGroup ->
                        UiGroup(domainGroup)
                    }
                    .sortedBy { it.debtLeft == BigDecimal.ZERO }
            }
        }
    }
}

data class UiGroup(
    val id: Long,
    val title: String,
    val debtLeft: BigDecimal,
) {
    constructor(domain: Group) : this(
        domain.id,
        domain.title,
        domain.debtLeft
    )
}