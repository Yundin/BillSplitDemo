package com.yundin.billsplitapp

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yundin.contactslist.ContactsListScreen
import com.yundin.grouplist.GroupListScreen
import com.yundin.navigation.Screen

@Composable
internal fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(navController, startDestination = Screen.Groups.route, modifier = modifier) {
        composable(Screen.Groups.route) { GroupListScreen() }
        composable(Screen.Contacts.route) { ContactsListScreen() }
        composable(Screen.AddContact.route) { /*TODO replace*/ GroupListScreen() }
        composable(Screen.AddGroup.route) { /*TODO replace*/ ContactsListScreen() }
    }
}
