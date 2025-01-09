package com.example.cheqapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cheqapp.presentation.Screen
import com.example.cheqapp.presentation.coin_details.components.CoinDetailScreen
import com.example.cheqapp.presentation.coin_list.components.CoinListScreen

object AppNavigation {
    @Composable
    fun SetupNavGraph(navController: NavHostController) {
        NavHost(
            navController = navController,
            startDestination = Screen.CoinListScreen.route
        ) {
            composable(
                route = Screen.CoinListScreen.route
            ) {
                CoinListScreen(navController)
            }

            composable(
                route = Screen.CoinDetailScreen.route + "/{coinId}"
            ) {
                CoinDetailScreen()
            }
        }
    }
}
