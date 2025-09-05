package com.example.calculator

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.calculator.MainRouteConstants.USER_DETAIL_PATTERN
import com.example.calculator.MainRouteConstants.USER_ID
import com.example.calculator.di.createViewModelFactory
import com.example.detail.DetailScreen
import com.example.detail.DetailViewModel
import com.example.home.HomeScreen
import com.example.home.HomeViewModel


@Composable
fun MainScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPaddings ->
        Box(modifier = Modifier.padding(innerPaddings)) {
            MainNavGraph()
        }
    }
}

@Composable
fun MainNavGraph() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = MainRoute.Home.path) {
        composable(route = MainRoute.Home.path) { backStackEntry ->

            val mainViewModel = ViewModelProvider(
                backStackEntry,
                createViewModelFactory {
                    HomeViewModel()
                })[HomeViewModel::class.java]

            HomeScreen(mainViewModel)
        }
        composable(
            route = USER_DETAIL_PATTERN,
            arguments = listOf(navArgument(USER_ID) { type = NavType.StringType })
        ) { backStackEntry ->

            val detailViewModel = ViewModelProvider(owner = backStackEntry,
                createViewModelFactory { DetailViewModel() })[DetailViewModel::class]

            val userId = backStackEntry.arguments?.getString(USER_ID)
            DetailScreen(detailViewModel,userId)
        }
    }
}