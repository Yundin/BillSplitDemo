package com.yundin.billsplitapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yundin.addcontact.AddContactScreen
import com.yundin.addgroup.AddGroupScreen
import com.yundin.choosecontacts.ChooseContactsScreen
import com.yundin.contactslist.ContactsListScreen
import com.yundin.grouplist.GroupListScreen
import com.yundin.navigation.Screen

@Composable
internal fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    showSnackbar: (String) -> Unit
) {
    NavHost(navController, startDestination = Screen.Groups.route, modifier = modifier) {
        composable(Screen.Groups.route) { GroupListScreen() }
        composable(Screen.Contacts.route) {
            ContactsListScreen(showSnackbar = showSnackbar)
        }
        composable(Screen.AddContact.route) {
            AddContactScreen(showSnackbar = showSnackbar)
        }
        composable(Screen.AddGroup.route) {
            AddGroupScreen(
                navController = navController,
                showSnackbar = showSnackbar
            )
        }
        composable(Screen.ChooseContacts.route) {
            ChooseContactsScreen(
                navController = navController,
                showSnackbar = showSnackbar
            )
        }
    }
}
