package com.yundin.core

import com.yundin.core.repository.GroupsRepository

interface GroupListDependencies {
    val groupsRepository: GroupsRepository
}
