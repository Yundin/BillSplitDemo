package com.yundin.core.repository

import com.yundin.core.model.Group
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

interface GroupsRepository {
    val groups: Flow<List<Group>>

    suspend fun addGroup(title: String, sum: BigDecimal, contactIds: List<Long>): Group
}
