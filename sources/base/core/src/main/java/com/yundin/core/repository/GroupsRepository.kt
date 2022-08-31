package com.yundin.core.repository

import com.yundin.core.model.Group
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

interface GroupsRepository {
    fun observeGroups(): Flow<List<Group>>

    fun getById(groupId: Long): Flow<Group>
    suspend fun addGroup(title: String, sum: BigDecimal, contactIds: List<Long>): Group
    suspend fun setContactChecked(groupId: Long, contactId: Long, checked: Boolean)
}
