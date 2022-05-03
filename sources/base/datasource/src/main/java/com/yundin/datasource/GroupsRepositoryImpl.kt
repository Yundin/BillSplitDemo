package com.yundin.datasource

import com.yundin.core.dagger.scope.AppScope
import com.yundin.core.model.Group
import com.yundin.core.model.GroupContact
import com.yundin.core.repository.GroupsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject

@AppScope
class GroupsRepositoryImpl @Inject constructor() : GroupsRepository {
    private val contacts = listOf(
        GroupContact(0, "Contact 1", BigDecimal.ONE),
        GroupContact(0, "Contact 2", BigDecimal.ZERO)
    )
    private var groupsList = listOf(
        Group(0, "Title 1", Date(), BigDecimal.ZERO, contacts),
        Group(0, "Title 2", Date(), BigDecimal.ONE, contacts),
    )
    private val groupsFlow: MutableStateFlow<List<Group>> =
        MutableStateFlow(groupsList)


    override val groups: Flow<List<Group>>
        get() = groupsFlow

    override fun addGroup(title: String, sum: BigDecimal, contactIds: List<Long>): Group {
        val newGroup = Group(
            id = groupsList.maxOf { it.id } + 1,
            title,
            Date(),
            amountSpent = sum,
            contacts
        )
        val newList = groupsList.toMutableList().apply {
            add(newGroup)
        }
        groupsFlow.value = newList
        groupsList = newList
        return newGroup
    }
}
