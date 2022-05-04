package com.yundin.groupdetails.di

import com.yundin.core.GroupDetailsDependencies
import com.yundin.core.dagger.FeatureViewModelBuilder
import com.yundin.core.dagger.FeatureViewModelComponent
import com.yundin.core.dagger.scope.FeatureScope
import com.yundin.groupdetails.GroupDetailsViewModel
import dagger.Component

@[FeatureScope Component(dependencies = [GroupDetailsDependencies::class])]
internal interface GroupDetailComponent: FeatureViewModelComponent<GroupDetailsViewModel> {

    @Component.Builder
    interface Builder : FeatureViewModelBuilder<GroupDetailsDependencies, GroupDetailsViewModel>
}
