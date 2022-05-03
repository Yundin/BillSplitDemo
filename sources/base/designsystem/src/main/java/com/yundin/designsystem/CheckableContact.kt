package com.yundin.designsystem

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp

@Composable
fun CheckableContactItem(
    name: String,
    checked: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = name)
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            Icons.Filled.Check,
            null,
            modifier = Modifier.alpha(if (checked) 1f else 0f)
        )
    }
}
