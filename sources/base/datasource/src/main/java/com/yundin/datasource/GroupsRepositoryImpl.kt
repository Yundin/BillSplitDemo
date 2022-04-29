package com.yundin.datasource

import com.yundin.core.model.Group
import com.yundin.core.model.GroupContact
import com.yundin.core.repository.GroupsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject

class GroupsRepositoryImpl @Inject constructor(): GroupsRepository {
    override val groups: Flow<List<Group>>
        get() = flow {
            val contacts = listOf(
                GroupContact(0, "Contact 1", BigDecimal.ONE),
                GroupContact(0, "Contact 2", BigDecimal.ZERO)
            )
            emit(
                listOf(
                    Group(0, "Title 1", Date(), BigDecimal.ZERO, contacts),
                    Group(0, "Title 2", Date(), BigDecimal.ONE, contacts),
                )
            )
        }
}
