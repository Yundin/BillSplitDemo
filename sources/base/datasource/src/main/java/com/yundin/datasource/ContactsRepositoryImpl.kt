package com.yundin.datasource

import com.yundin.core.model.Contact
import com.yundin.core.repository.ContactsRepository
import com.yundin.core.scope.AppScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.math.BigDecimal
import javax.inject.Inject

@AppScope
class ContactsRepositoryImpl @Inject constructor() : ContactsRepository {
    private var contactList = listOf(
        Contact(0, "Contact 1", BigDecimal.ZERO),
        Contact(0, "Contact 2", BigDecimal.ONE)
    )
    private val contactsFlow: MutableStateFlow<List<Contact>> =
        MutableStateFlow(contactList)

    override val contacts: Flow<List<Contact>>
        get() = contactsFlow


    override fun addContact(name: String) {
        val newList = contactList.toMutableList().apply {
            add(
                Contact(
                    id = 0,
                    name = name,
                    owesOverall = BigDecimal.ZERO
                )
            )
        }
        contactsFlow.value = newList
        contactList = newList
    }
}
