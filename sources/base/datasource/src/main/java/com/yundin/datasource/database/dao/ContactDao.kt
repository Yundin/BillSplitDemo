package com.yundin.datasource.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.yundin.datasource.database.entity.ContactEntity
import com.yundin.datasource.database.entity.GroupContactCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Query("SELECT * FROM contacts")
    fun getAll(): Flow<List<ContactEntity>>

    @Query("SELECT * FROM contacts WHERE contactId = :id")
    suspend fun getById(id: Long): ContactEntity

    @Insert
    suspend fun addContact(contact: ContactEntity): Long

    @Query("DELETE FROM contacts WHERE contactid = :id")
    suspend fun removeContact(id: Long)

    @Query("SELECT * FROM groupcontactcrossref")
    suspend fun getJoinTable(): List<GroupContactCrossRef>
}
