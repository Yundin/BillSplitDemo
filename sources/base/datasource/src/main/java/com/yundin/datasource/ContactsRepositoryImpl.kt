package com.yundin.datasource

import com.yundin.core.dagger.scope.AppScope
import com.yundin.core.model.Contact
import com.yundin.core.repository.ContactsRepository
import com.yundin.datasource.database.dao.ContactDao
import com.yundin.datasource.database.entity.ContactEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@AppScope
class ContactsRepositoryImpl @Inject constructor(
    private val contactDao: ContactDao
) : ContactsRepository {
    override val contacts: Flow<List<Contact>>
        get() = contactDao.getAll().map { contacts ->
            contacts.map { it.toDomain() }
        }

    override suspend fun addContact(name: String): Contact {
        val addedId = contactDao.addContact(
            ContactEntity(0, name)
        )
        return contactDao.getById(addedId).toDomain()
    }
}
