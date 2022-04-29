package com.yundin.core.repository

import com.yundin.core.model.Group
import kotlinx.coroutines.flow.Flow

interface GroupsRepository {
    val groups: Flow<List<Group>>
}
