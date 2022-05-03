package com.yundin.datasource

import com.yundin.core.dagger.scope.AppScope
import com.yundin.core.model.Contact
import com.yundin.core.repository.ContactsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.math.BigDecimal
import javax.inject.Inject

@AppScope
class ContactsRepositoryImpl @Inject constructor() : ContactsRepository {
    private var contactList = listOf(
        Contact(0, "Contact 1", BigDecimal.ZERO),
        Contact(1, "Contact 2", BigDecimal.ONE)
    )
    private val contactsFlow: MutableStateFlow<List<Contact>> =
        MutableStateFlow(contactList)

    override val contacts: Flow<List<Contact>>
        get() = contactsFlow


    override fun addContact(name: String): Contact {
        if (contactList.any { it.name == name }) {
            throw IllegalArgumentException("Contact already exists")
        }
        val contact = Contact(
            id = contactList.maxOf { it.id } + 1,
            name = name,
            owesOverall = BigDecimal.ZERO
        )
        val newList = contactList.toMutableList().apply {
            add(contact)
        }
        contactsFlow.value = newList
        contactList = newList
        return contact
    }
}
