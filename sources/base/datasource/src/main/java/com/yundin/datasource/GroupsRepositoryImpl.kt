package com.yundin.datasource

import com.yundin.core.dagger.scope.AppScope
import com.yundin.core.model.Group
import com.yundin.core.repository.GroupsRepository
import com.yundin.datasource.database.dao.GroupDao
import com.yundin.datasource.database.entity.GroupContactCrossRef
import com.yundin.datasource.database.entity.GroupEntity
import com.yundin.datasource.database.entity.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject

@AppScope
class GroupsRepositoryImpl @Inject constructor(
    private val groupDao: GroupDao
) : GroupsRepository {

    override fun observeGroups(): Flow<List<Group>> {
        return groupDao.getGroupWithContactsJunction().map { groupsWithContactsList ->
            groupsWithContactsList.map {
                it.toDomain()
            }
        }
    }

    override fun getById(groupId: Long): Flow<Group> {
        return groupDao.getGroupWithContactsJunctionById(groupId).map { groupWithContacts ->
            groupWithContacts.toDomain()
        }
    }

    override suspend fun addGroup(title: String, sum: BigDecimal, contactIds: List<Long>): Group {
        val groupId = groupDao.addGroup(
            GroupEntity(
                groupId = 0,
                title = title,
                createdDate = Date(),
                checkAmount = sum.setScale(2)
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
        return getById(groupId).first()
    }

    override suspend fun setContactChecked(
        groupId: Long,
        contactId: Long,
        checked: Boolean
    ) {
        val ref = groupDao.getJoinRef(groupId, contactId).first()
        groupDao.updateJoinRef(ref.copy(checked = checked))
    }
}
