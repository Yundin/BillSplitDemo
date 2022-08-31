package com.yundin.grouplist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.yundin.core.model.Group
import com.yundin.core.repository.GroupsRepository
import kotlinx.coroutines.flow.map
import java.math.BigDecimal
import javax.inject.Inject

class GroupListViewModel @Inject constructor(
    private val groupsRepository: GroupsRepository
) : ViewModel() {
    val groups: LiveData<List<UiGroup>> = groupsRepository.observeGroups()
        .map { domainList ->
            domainList
                .sortedBy { it.dateCreated }
                .map { domainGroup ->
                    UiGroup(domainGroup)
                }
                .sortedBy { it.debtLeft == BigDecimal.ZERO }
        }
        .asLiveData(viewModelScope.coroutineContext)
}

data class UiGroup(
    val id: Long,
    val title: String,
    val debtLeft: BigDecimal,
)

internal fun UiGroup(domain: Group): UiGroup {
    return UiGroup(
        domain.id,
        domain.title,
        domain.debtLeft
    )
}
