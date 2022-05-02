package com.yundin.mainscreen

import androidx.annotation.StringRes
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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
        bottomBar = { BottomBar(navController) },
        floatingActionButton = { MainFloatingActionButton(navController) }
    ) { innerPadding ->
        navHost(innerPadding)
    }
}

@Composable
private fun BottomBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentTab = MainTab.getByRoute(currentDestination?.route)
    BottomBarContent(
        visible = currentTab != null,
        isSelected = { screen ->
            currentDestination?.hierarchy?.any { it.route == screen.route } == true
        },
        navigate = { screen ->
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

@Composable
private fun BottomBarContent(
    visible: Boolean,
    isSelected: (Screen) -> Boolean,
    navigate: (Screen) -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically { it },
        exit = slideOutVertically { it }
    ) {
        BottomNavigation {
            MainTab.values().forEach { tab ->
                BottomNavigationItem(
                    icon = { Icon(tab.barIcon, contentDescription = null) },
                    label = { Text(stringResource(tab.barTitleRes)) },
                    selected = isSelected(tab.screen),
                    onClick = {
                        navigate(tab.screen)
                    }
                )
            }

        }
    }
}

@Composable
private fun MainFloatingActionButton(navController: NavController) {
    val destinationRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val currentTab = MainTab.getByRoute(destinationRoute)
    MainFloatingActionButtonContent(
        visible = currentTab != null,
        textRes = currentTab?.fabTextRes,
        onClick = { currentTab?.fabScreen?.route?.let { navController.navigate(it) } }
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun MainFloatingActionButtonContent(
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

private enum class MainTab(
    val screen: Screen,
    @StringRes val barTitleRes: Int,
    val barIcon: ImageVector,
    @StringRes val fabTextRes: Int,
    val fabScreen: Screen,
) {
    GROUPS(
        screen = Screen.Groups,
        barTitleRes = R.string.groups_bottom_bar_title,
        barIcon = Icons.Filled.Receipt,
        fabTextRes = R.string.new_group_fab,
        fabScreen = Screen.AddGroup,
    ),
    CONTACTS(
        screen = Screen.Contacts,
        barTitleRes = R.string.contacts_bottom_bar_title,
        barIcon = Icons.Filled.AccountCircle,
        fabTextRes = R.string.new_contact_fab,
        fabScreen = Screen.AddContact,
    );

    val route: String
        get() = screen.route

    companion object {
        fun getByRoute(route: String?): MainTab? =
            values().firstOrNull { it.screen.route == route }
    }
}