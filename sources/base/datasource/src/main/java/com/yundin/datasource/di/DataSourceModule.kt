package com.yundin.datasource.di

import com.yundin.core.repository.ContactsRepository
import com.yundin.core.repository.GroupsRepository
import com.yundin.datasource.ContactsRepositoryImpl
import com.yundin.datasource.GroupsRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface DataSourceModule {
    @Binds
    fun bindGroupRepository(repository: GroupsRepositoryImpl): GroupsRepository

    @Binds
    fun bindContactsRepository(repository: ContactsRepositoryImpl): ContactsRepository
}
