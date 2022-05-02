package com.yundin.designsystem

import androidx.annotation.StringRes
import androidx.compose.animation.*
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppFloatingActionButton(
    visible: Boolean,
    @StringRes textRes: Int?,
    onClick: () -> Unit,
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically { it * 2 },
        exit = slideOutVertically { it * 2 },
    ) {
        FloatingActionButton(onClick = onClick) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(
                    start = 20.dp,
                    end = 20.dp
                ),
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
                Spacer(Modifier.width(12.dp))
                AnimatedContent(
                    targetState = textRes,
                    transitionSpec = { slideInVertically { -it } with slideOutVertically { it } }
                ) { textRes ->
                    textRes?.let { Text(stringResource(it)) }
                }
            }
        }
    }
}