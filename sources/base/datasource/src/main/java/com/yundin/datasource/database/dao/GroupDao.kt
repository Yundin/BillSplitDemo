package com.yundin.datasource.database.dao

import androidx.room.*
import com.yundin.datasource.database.entity.GroupContactCrossRef
import com.yundin.datasource.database.entity.GroupEntity
import com.yundin.datasource.database.entity.GroupWithContacts
import kotlinx.coroutines.flow.Flow

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

    @Query("SELECT * FROM groupcontactcrossref " +
            "where groupId = :groupId AND contactId = :contactId")
    fun getJoinRef(groupId: Long, contactId: Long): Flow<GroupContactCrossRef>

    @Update
    suspend fun updateJoinRef(ref: GroupContactCrossRef)
}
