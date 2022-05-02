package com.yundin.mainscreen

import androidx.annotation.StringRes
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.yundin.designsystem.AppBottomBar
import com.yundin.designsystem.AppFloatingActionButton
import com.yundin.designsystem.BottomBarTab
import com.yundin.navigation.Screen

@Composable
fun MainScreenUI(
    navController: NavController,
    scaffoldState: ScaffoldState,
    navHost: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        scaffoldState = scaffoldState,
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
        isTabSelected = { screen ->
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
    isTabSelected: (Screen) -> Boolean,
    navigate: (Screen) -> Unit
) {
    AppBottomBar(
        visible = visible,
        tabs = {
            MainTab.values().forEach { tab ->
                BottomBarTab(
                    icon = tab.barIcon,
                    titleRes = tab.barTitleRes,
                    isSelected = isTabSelected(tab.screen),
                    navigate = { navigate(tab.screen) },
                )
            }
        }
    )
}

@Composable
private fun MainFloatingActionButton(navController: NavController) {
    val destinationRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val currentTab = MainTab.getByRoute(destinationRoute)
    AppFloatingActionButton(
        visible = currentTab != null,
        textRes = currentTab?.fabTextRes,
        onClick = { currentTab?.fabScreen?.route?.let { navController.navigate(it) } }
    )
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
