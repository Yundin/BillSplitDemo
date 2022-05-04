package com.yundin.groupdetails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yundin.core.App
import com.yundin.core.model.Group
import com.yundin.core.model.GroupContact
import com.yundin.core.utils.daggerViewModelFactory
import com.yundin.designsystem.CheckableContactItem
import com.yundin.groupdetails.di.DaggerGroupDetailComponent

@Composable
fun GroupDetailsScreen(
    groupId: Long
) {
    val app = LocalContext.current.applicationContext as App
    val viewModel: GroupDetailsViewModel = viewModel(
        factory = daggerViewModelFactory(
            dependencies = app.getAppProvider(),
            builder = DaggerGroupDetailComponent.builder()
        )
    )
    LaunchedEffect(key1 = groupId) {
        viewModel.setId(groupId)
    }
    val group by viewModel.uiGroup.observeAsState()
    group?.let {
        GroupDetailContent(
            group = it,
            onContactClick = viewModel::onContactClick
        )
    }
}

@Composable
private fun GroupDetailContent(
    group: Group,
    onContactClick: (GroupContact) -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = group.title,
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Text(
            text = stringResource(R.string.check_amount_format, group.amountSpent.toPlainString()),
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(
                horizontal = 16.dp,
            )
        )
        Text(
            text = stringResource(R.string.debt_per_person_format, group.debtPerPerson.toPlainString()),
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(
                horizontal = 16.dp,
            )
        )
        Text(
            text = stringResource(R.string.debt_left_format, group.debtLeft.toPlainString()),
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.participants_subtitle),
            style = MaterialTheme.typography.caption,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        group.contacts.forEach { contact ->
            CheckableContactItem(
                name = contact.name,
                checked = contact.checked,
                modifier = Modifier
                    .clickable {
                        onContactClick(contact)
                    }
                    .alpha(if (contact.checked) 0.6f else 1f)
            )
        }
    }
}
