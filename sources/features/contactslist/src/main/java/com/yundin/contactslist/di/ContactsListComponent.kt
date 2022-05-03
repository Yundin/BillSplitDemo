package com.yundin.contactslist.di

import com.yundin.contactslist.ContactsListViewModel
import com.yundin.core.ContactsListDependencies
import com.yundin.core.dagger.scope.FeatureScope
import dagger.Component

@[FeatureScope Component(dependencies = [ContactsListDependencies::class])]
internal interface ContactsListComponent {

    val viewModel: ContactsListViewModel
}
