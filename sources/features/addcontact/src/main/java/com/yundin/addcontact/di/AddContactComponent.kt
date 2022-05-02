package com.yundin.addcontact.di

import com.yundin.addcontact.AddContactViewModel
import com.yundin.core.AddContactDependencies
import com.yundin.core.scope.FeatureScope
import dagger.Component

@[FeatureScope Component(dependencies = [AddContactDependencies::class])]
internal interface AddContactComponent {

    val viewModel: AddContactViewModel
}
