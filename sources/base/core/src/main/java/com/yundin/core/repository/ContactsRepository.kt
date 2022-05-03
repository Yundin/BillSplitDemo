package com.yundin.core.repository

import com.yundin.core.model.Contact
import kotlinx.coroutines.flow.Flow
import java.lang.IllegalArgumentException
import kotlin.jvm.Throws

interface ContactsRepository {
    val contacts: Flow<List<Contact>>

    /**
     * @throws IllegalArgumentException when contact with [name] already exists
     */
    @Throws(IllegalArgumentException::class)
    fun addContact(name: String): Contact
}