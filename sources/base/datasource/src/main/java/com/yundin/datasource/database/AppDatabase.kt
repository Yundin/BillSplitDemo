package com.yundin.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yundin.datasource.database.converter.BigDecimalTypeConverter
import com.yundin.datasource.database.converter.DateConverter
import com.yundin.datasource.database.dao.ContactDao
import com.yundin.datasource.database.dao.GroupDao
import com.yundin.datasource.database.entity.ContactEntity
import com.yundin.datasource.database.entity.GroupContactCrossRef
import com.yundin.datasource.database.entity.GroupEntity

@Database(
    entities = [
        ContactEntity::class,
        GroupEntity::class,
        GroupContactCrossRef::class
    ],
    version = 1
)
@TypeConverters(BigDecimalTypeConverter::class, DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contactsDao(): ContactDao
    abstract fun groupDao(): GroupDao
}
