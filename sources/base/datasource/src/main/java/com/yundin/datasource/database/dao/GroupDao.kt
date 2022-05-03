package com.yundin.datasource.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.yundin.core.model.Group
import com.yundin.datasource.database.entity.GroupContactCrossRef
import com.yundin.datasource.database.entity.GroupEntity
import com.yundin.datasource.database.entity.GroupWithContacts
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Dao
interface GroupDao {
    @Transaction
    @Query("SELECT * FROM groups")
    fun getGroupWithContactsJunction(): Flow<List<GroupWithContacts>>

    @Transaction
    @Query("SELECT * FROM groups where groupId = :groupId")
    fun getGroupWithContactsJunctionById(groupId: Long): Flow<GroupWithContacts>

    @Insert
    suspend fun addGroup(group: GroupEntity): Long

    @Insert
    suspend fun addGroupPersonJoin(refs: List<GroupContactCrossRef>)

    fun getGroups(): Flow<List<Group>> {
        return getGroupWithContactsJunction().map { list ->
            list.map {
                it.toDomain()
            }
        }
    }

    fun getGroupById(id: Long): Flow<Group> {
        return getGroupWithContactsJunctionById(id).map { group ->
            group.toDomain()
        }
    }
}
