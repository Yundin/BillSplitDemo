package com.yundin.grouplist.di

import com.yundin.core.GroupListDependencies
import com.yundin.core.dagger.scope.FeatureScope
import com.yundin.grouplist.GroupListViewModel
import dagger.Component

@[FeatureScope Component(dependencies = [GroupListDependencies::class])]
internal interface GroupListComponent {

    val viewModel: GroupListViewModel
}
