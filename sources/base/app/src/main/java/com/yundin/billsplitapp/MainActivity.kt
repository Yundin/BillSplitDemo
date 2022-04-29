package com.yundin.billsplitapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yundin.designsystem.theme.BillSplitAppTheme
import com.yundin.grouplist.GroupListScreen
import com.yundin.mainscreen.MainScreenUI
import com.yundin.navigation.Screen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BillSplitAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    MainScreenUI(navController) { innerPadding ->
                        NavHost(navController, startDestination = Screen.Groups.route, Modifier.padding(innerPadding)) {
                            composable(Screen.Groups.route) { GroupListScreen() }
                            composable(Screen.Contacts.route) { Text("Contacts") }
                        }
                    }
                }
            }
        }
    }
}
