package com.yundin.core.repository

import com.yundin.core.model.Contact
import kotlinx.coroutines.flow.Flow

interface ContactsRepository {
    val contacts: Flow<List<Contact>>

    fun addContact(name: String)
}