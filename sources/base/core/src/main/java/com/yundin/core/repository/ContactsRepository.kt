package com.yundin.core.repository

import android.database.sqlite.SQLiteConstraintException
import com.yundin.core.model.Contact
import kotlinx.coroutines.flow.Flow

interface ContactsRepository {
    val contacts: Flow<List<Contact>>

    /**
     * @throws SQLiteConstraintException when contact with [name] already exists
     */
    @Throws(SQLiteConstraintException::class)
    suspend fun addContact(name: String): Contact

    /**
     * @return true if contact was deleted, false if there is debt on this contact
     */
    suspend fun removeContact(id: Long): Boolean
}