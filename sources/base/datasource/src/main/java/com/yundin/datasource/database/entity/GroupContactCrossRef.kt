package com.yundin.datasource.database.entity

import androidx.room.*
import com.yundin.core.model.Group
import com.yundin.core.model.GroupContact

@Entity(
    primaryKeys = ["groupId", "contactId"]
)
data class GroupContactCrossRef(
    val groupId: Long,
    @ColumnInfo(index = true) val contactId: Long,
    val checked: Boolean
)

data class GroupWithContacts(
    @Embedded
    val group: GroupEntity,
    @Relation(
        entity = GroupContactCrossRef::class,
        entityColumn = "groupId",
        parentColumn = "groupId"
    )
    val groupContacts: List<GroupContactCrossRef>,
    @Relation(
        parentColumn = "groupId",
        entity = ContactEntity::class,
        entityColumn = "contactId",
        associateBy = Junction(GroupContactCrossRef::class)
    )
    val contacts: List<ContactEntity>,
) {
    fun toDomain(): Group {
        val checkedContactsMap = groupContacts.associate { it.contactId to it.checked }
        return Group(
            id = group.groupId,
            title = group.title,
            dateCreated = group.createdDate,
            amountSpent = group.checkAmount,
            participantsCount = groupContacts.size,
            contacts = contacts.map { contactEntity ->
                GroupContact(
                    contactEntity.contactId,
                    contactEntity.name,
                    checkedContactsMap.getValue(contactEntity.contactId)
                )
            }
        )
    }
}
