package com.yundin.navigation

sealed class Screen(val route: String) {
    object Groups : Screen("groups")
    object Contacts : Screen("contacts")
}
