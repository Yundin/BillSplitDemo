package com.yundin.addgroup.di

import com.yundin.addgroup.AddGroupViewModel
import com.yundin.core.AddGroupDependencies
import com.yundin.core.scope.FeatureScope
import dagger.Component

@[FeatureScope Component(dependencies = [AddGroupDependencies::class])]
internal interface AddGroupComponent {

    val viewModel: AddGroupViewModel
}
