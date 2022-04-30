package com.yundin.datasource

import com.yundin.core.model.Contact
import com.yundin.core.repository.ContactsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.math.BigDecimal
import javax.inject.Inject

class ContactsRepositoryImpl @Inject constructor(): ContactsRepository {
    override val contacts: Flow<List<Contact>>
        get() = flow {
            emit(
                listOf(
                    Contact(0, "Contact 1", BigDecimal.ZERO),
                    Contact(0, "Contact 2", BigDecimal.ONE)
                )
            )
        }
}
