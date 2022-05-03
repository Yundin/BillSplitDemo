package com.yundin.datasource.di

import android.content.Context
import androidx.room.Room
import com.yundin.core.dagger.scope.AppScope
import com.yundin.core.repository.ContactsRepository
import com.yundin.core.repository.GroupsRepository
import com.yundin.datasource.ContactsRepositoryImpl
import com.yundin.datasource.GroupsRepositoryImpl
import com.yundin.datasource.database.AppDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataSourceModule {
    @Binds
    fun bindGroupRepository(repository: GroupsRepositoryImpl): GroupsRepository

    @Binds
    fun bindContactsRepository(repository: ContactsRepositoryImpl): ContactsRepository

    companion object {
        @Provides
        fun provideContactDao(appDatabase: AppDatabase) = appDatabase.contactsDao()

        @Provides
        fun provideGroupDao(appDatabase: AppDatabase) = appDatabase.groupDao()

        @Provides
        @AppScope
        fun provideAppDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "billsplit"
            ).build()
        }
    }
}
