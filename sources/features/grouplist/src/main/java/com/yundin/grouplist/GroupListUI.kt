package com.yundin.grouplist

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yundin.core.App
import com.yundin.core.model.Group
import com.yundin.core.utils.daggerViewModelFactory
import com.yundin.designsystem.GroupItem
import com.yundin.grouplist.di.DaggerGroupListComponent

@Composable
fun GroupListScreen() {
    val app = LocalContext.current.applicationContext as App
    val viewModel: GroupListViewModel = viewModel(
        factory = daggerViewModelFactory {
            DaggerGroupListComponent.builder()
                .groupListDependencies(app.getAppProvider())
                .build()
                .viewModel
        }
    )
    val groups: List<Group>? by viewModel.groups.observeAsState()
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(groups.orEmpty()) { group ->
            GroupItem(title = group.title, overallDebt = group.amountSpent)
        }
    }
    if (groups.isNullOrEmpty()) {
        Text(
            textAlign = TextAlign.Center,
            text = stringResource(R.string.no_groups),
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight()
        )
    }
}
