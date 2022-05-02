package com.yundin.billsplitapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.yundin.designsystem.theme.BillSplitAppTheme
import com.yundin.mainscreen.MainScreenUI
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainContent()
        }
    }
}

@Composable
private fun MainContent() {
    BillSplitAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            val navController = rememberNavController()
            val scaffoldState = rememberScaffoldState()
            val snackbarCoroutineScope = rememberCoroutineScope()
            val showSnackbar: (String) -> Unit = { text: String ->
                snackbarCoroutineScope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(text)
                }
            }
            MainScreenUI(navController, scaffoldState) { innerPadding ->
                AppNavigation(
                    navController = navController,
                    modifier = Modifier.padding(innerPadding),
                    showSnackbar = showSnackbar
                )
            }
        }
    }
}
