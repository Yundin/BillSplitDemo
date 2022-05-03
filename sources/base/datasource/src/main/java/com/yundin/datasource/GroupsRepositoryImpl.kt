package com.yundin.datasource

import com.yundin.core.dagger.scope.AppScope
import com.yundin.core.model.Group
import com.yundin.core.repository.GroupsRepository
import com.yundin.datasource.database.dao.GroupDao
import com.yundin.datasource.database.entity.GroupContactCrossRef
import com.yundin.datasource.database.entity.GroupEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject

@AppScope
class GroupsRepositoryImpl @Inject constructor(
    private val groupDao: GroupDao
) : GroupsRepository {
    override val groups: Flow<List<Group>>
        get() = groupDao.getGroups()

    override suspend fun addGroup(title: String, sum: BigDecimal, contactIds: List<Long>): Group {
        val groupId = groupDao.addGroup(
            GroupEntity(
                groupId = 0,
                title = title,
                createdDate = Date(),
                checkAmount = sum
            )
        )
        groupDao.addGroupPersonJoin(
            contactIds.map {
                GroupContactCrossRef(
                    groupId = groupId,
                    contactId = it,
                    checked = false
                )
            }
        )
        return groupDao.getGroupById(groupId).first()
    }
}
