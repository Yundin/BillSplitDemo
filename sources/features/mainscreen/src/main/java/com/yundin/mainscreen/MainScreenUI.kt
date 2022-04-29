package com.yundin.mainscreen

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.yundin.navigation.Screen

@Composable
fun MainScreenUI(
    navController: NavController,
    navHost: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) { innerPadding ->
        navHost(innerPadding)
    }
}

@Composable
private fun BottomBar(navController: NavController) {
    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        listOf(
            BottomNavigationScreen.Groups,
            BottomNavigationScreen.Contacts,
        ).forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                label = { Text(stringResource(screen.resourceId)) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }

    }
}

private sealed class BottomNavigationScreen(val route: String, @StringRes val resourceId: Int) {
    object Groups : BottomNavigationScreen(Screen.Groups.route, R.string.groups_bottom_bar_title)
    object Contacts : BottomNavigationScreen(Screen.Contacts.route, R.string.contacts_bottom_bar_title)
}