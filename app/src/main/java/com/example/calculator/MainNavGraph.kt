package com.example.calculator

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.calculator.di.createViewModelFactory
import com.example.detail.DetailScreen
import com.example.detail.DetailViewModel
import com.example.home.HomeScreen
import com.example.home.HomeViewModel


@Composable
fun MainScreen() {

    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(), bottomBar = {
            NavigationBar(modifier = Modifier.height(60.dp)) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            navController.navigate(MainRoute.Home.path)
                        }, contentAlignment = Alignment.Center
                ) {
                    Image(imageVector = Icons.Default.DateRange, contentDescription = null)
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            navController.navigate(MainRoute.Detail.path)
                        }, contentAlignment = Alignment.Center
                ) {
                    Image(imageVector = Icons.AutoMirrored.Filled.List, contentDescription = null)
                }
            }
        }) { innerPaddings ->
        Box(modifier = Modifier.padding(innerPaddings)) {
            MainNavGraph(navController)
        }
    }
}

@Composable
fun MainNavGraph(navController: NavHostController) {

    val context = LocalContext.current
    val appModules = (context.applicationContext as MyApplication).getWikipediaModule()

    val homeViewModel: HomeViewModel = viewModel(
        factory = createViewModelFactory { HomeViewModel(appModules.addDiaryUseCase()) }
    )

    val detailViewModel: DetailViewModel = viewModel(
        factory = createViewModelFactory { DetailViewModel(appModules.getDiaryListUseCase()) }
    )

    NavHost(navController = navController, startDestination = MainRoute.Home.path) {
        composable(route = MainRoute.Home.path) {
            HomeScreen(homeViewModel)
        }
        composable(route = MainRoute.Detail.path) {
            DetailScreen(detailViewModel)
        }
    }
}