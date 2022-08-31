package com.yundin.core

import com.yundin.core.repository.ContactsRepository
import com.yundin.core.repository.GroupsRepository

interface GroupListDependencies {
    val groupsRepository: GroupsRepository
}

interface ContactsListDependencies {
    val contactsRepository: ContactsRepository
}

interface AddContactDependencies {
    val contactsRepository: ContactsRepository
}

interface AddGroupDependencies {
    val groupsRepository: GroupsRepository
}

interface ChooseContactsDependencies {
    val contactsRepository: ContactsRepository
}

interface GroupDetailsDependencies {
    val groupsRepository: GroupsRepository
}
