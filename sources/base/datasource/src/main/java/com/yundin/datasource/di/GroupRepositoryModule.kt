package com.yundin.datasource.di

import com.yundin.core.repository.GroupsRepository
import com.yundin.datasource.GroupsRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface GroupRepositoryModule {
    @Binds
    fun bindGroupRepository(repository: GroupsRepositoryImpl): GroupsRepository
}
