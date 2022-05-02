package com.yundin.designsystem

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource

@Composable
fun AppBottomBar(
    visible: Boolean,
    tabs: @Composable RowScope.() -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically { it },
        exit = slideOutVertically { it }
    ) {
        BottomNavigation {
            tabs(this)
        }
    }
}

@Composable
fun RowScope.BottomBarTab(
    icon: ImageVector,
    @StringRes titleRes: Int,
    isSelected: Boolean,
    navigate: () -> Unit
) {
    BottomNavigationItem(
        icon = { Icon(icon, contentDescription = null) },
        label = { Text(stringResource(titleRes)) },
        selected = isSelected,
        onClick = navigate
    )
}
