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
}