package com.yundin.datasource.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.yundin.core.model.Contact

@Entity(
    tableName = "contacts",
    indices = [Index(value = ["name"], unique = true)]
)
data class ContactEntity(
    @PrimaryKey(autoGenerate = true) val contactId: Long,
    @ColumnInfo(name = "name") val name: String,
)

internal fun ContactEntity.toDomain() = Contact(contactId, name)
