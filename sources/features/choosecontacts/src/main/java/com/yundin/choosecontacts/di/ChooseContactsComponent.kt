package com.yundin.choosecontacts.di

import com.yundin.choosecontacts.ChooseContactsViewModel
import com.yundin.core.ChooseContactsDependencies
import com.yundin.core.dagger.FeatureViewModelBuilder
import com.yundin.core.dagger.FeatureViewModelComponent
import com.yundin.core.dagger.scope.FeatureScope
import dagger.Component

@[FeatureScope Component(dependencies = [ChooseContactsDependencies::class])]
internal interface ChooseContactsComponent: FeatureViewModelComponent<ChooseContactsViewModel> {

    @Component.Builder
    interface Builder: FeatureViewModelBuilder<ChooseContactsDependencies, ChooseContactsViewModel>
}
