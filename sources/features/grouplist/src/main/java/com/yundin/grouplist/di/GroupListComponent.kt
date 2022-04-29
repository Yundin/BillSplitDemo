package com.yundin.grouplist.di

import com.yundin.core.GroupListDependencies
import com.yundin.core.scope.FeatureScope
import com.yundin.grouplist.GroupListViewModel
import dagger.Component

@[FeatureScope Component(dependencies = [GroupListDependencies::class])]
internal interface GroupListComponent {

    val viewModel: GroupListViewModel
}
