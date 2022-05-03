package com.yundin.core

import com.yundin.core.repository.ContactsRepository
import com.yundin.core.repository.GroupsRepository
import com.yundin.core.utils.ResourceProvider

interface GroupListDependencies {
    val groupsRepository: GroupsRepository
}

interface ContactsListDependencies {
    val contactsRepository: ContactsRepository
}

interface AddContactDependencies {
    val contactsRepository: ContactsRepository
    val resourceProvider: ResourceProvider
}

interface AddGroupDependencies {
    val groupsRepository: GroupsRepository
    val resourceProvider: ResourceProvider
}
