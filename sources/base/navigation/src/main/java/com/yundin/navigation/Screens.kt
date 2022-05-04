package com.yundin.navigation

sealed class Screen(val route: String) {
    object Groups : Screen("groups")
    object Contacts : Screen("contacts")
    object AddContact : Screen("contacts/add")
    object AddGroup : Screen("groups/add")
    object ChooseContacts : Screen("contacts/choose?selectedIds={selectedIds}")
}
