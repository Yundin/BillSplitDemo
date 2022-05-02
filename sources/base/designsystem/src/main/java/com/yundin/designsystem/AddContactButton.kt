package com.yundin.designsystem

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun AddContactButton(
    contactName: String?,
    onClick: () -> Unit
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Surface(
            color = MaterialTheme.colors.primary,
            shape = CircleShape,
        ) {
            Icon(
                Icons.Filled.Person,
                null,
                Modifier.padding(4.dp)
            )
        }
        Spacer(Modifier.width(12.dp))
        val text = if (contactName.isNullOrBlank()) {
            stringResource(R.string.enter_name_hint)
        } else {
            stringResource(R.string.add_contact_btn_format, contactName.orEmpty())
        }
        Text(text = text)
    }
}