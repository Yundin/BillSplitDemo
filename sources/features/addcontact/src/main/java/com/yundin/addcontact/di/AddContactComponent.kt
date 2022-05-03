package com.yundin.addcontact.di

import com.yundin.addcontact.AddContactViewModel
import com.yundin.core.AddContactDependencies
import com.yundin.core.dagger.FeatureViewModelBuilder
import com.yundin.core.dagger.FeatureViewModelComponent
import com.yundin.core.dagger.scope.FeatureScope
import dagger.Component

@[FeatureScope Component(dependencies = [AddContactDependencies::class])]
internal interface AddContactComponent: FeatureViewModelComponent<AddContactViewModel> {

    @Component.Builder
    interface Builder : FeatureViewModelBuilder<AddContactDependencies, AddContactViewModel>
}
