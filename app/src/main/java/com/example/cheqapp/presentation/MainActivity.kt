package com.example.cheqapp.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cheqapp.common.Constants
import com.example.cheqapp.presentation.bottomsheet.ComposableBottomSheet
import com.example.cheqapp.presentation.navigation.AppNavigation
import com.example.cheqapp.ui.theme.CheqAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CheqAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    Scaffold(
                        topBar = { TopBar(navController) {
                            showBottomSheet()
                        }
                        }, bottomBar = {}, floatingActionButton = {}
                    ) {paddingValues ->
                        AppNavigation.SetupNavGraph(navController = navController, appModifier = Modifier.padding(paddingValues))
                    }
                }
            }
        }
    }

    private fun showBottomSheet() {
        val bottomSheet = ComposableBottomSheet.newInstance(Constants.AppInfo, 1)
        bottomSheet.show(supportFragmentManager, "ComposableBottomSheet")
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController, infoIconClick: () -> Unit) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    when (currentRoute) {
        Screen.CoinListScreen.route -> TopAppBar(title = {
            Text(text = "Crypto Currency App",
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                fontSize = 16.sp
            )}, actions = {
            IconButton(onClick = {
                infoIconClick()
            }, modifier = Modifier.height(20.dp)) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Search",
                )
            }
        }, colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary, // Background color
                titleContentColor = MaterialTheme.colorScheme.onPrimary, // Title text color
                actionIconContentColor = MaterialTheme.colorScheme.onPrimary // Action icon color
            ))

        Screen.CoinDetailScreen.route -> TopAppBar(title = {
            Text(text = "Crypto Currency 1",
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                fontSize = 16.sp
            )}
        )

        else ->   TopAppBar(title = {
            Text(text = "Coin Info",
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                fontSize = 16.sp
            )
        },
            navigationIcon = {
                IconButton(onClick = {navController.popBackStack()}, modifier = Modifier.height(20.dp)) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Info"
                    )
                }
            }
        )
    }
}