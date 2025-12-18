package com.example.pertemuan12.uicontroller

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pertemuan12.uicontroller.route.DestinasiDetail
import com.example.pertemuan12.uicontroller.route.DestinasiEdit
import com.example.pertemuan12.uicontroller.route.DestinasiEntry
import com.example.pertemuan12.uicontroller.route.DestinasiHome
import com.example.pertemuan12.view.DetailScreen
import com.example.pertemuan12.view.EntrySiswaScreen
import com.example.pertemuan12.view.HomeScreen
import com.example.pertemuan12.view.ItemEditScreen

@Composable
fun DataSiswaApp(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier // <--- KITA TAMBAHKAN INI BIAR MAIN ACTIVITY SENENG
) {
    Scaffold(
        modifier = modifier
    ) { innerPadding ->
        HostNavigasi(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun HostNavigasi(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = modifier
    ) {
        composable(DestinasiHome.route) {
            HomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route) },
                onDetailClick = { nama ->
                    navController.navigate("${DestinasiDetail.route}/$nama")
                }
            )
        }
        composable(DestinasiEntry.route) {
            EntrySiswaScreen(
                navigateBack = { navController.navigateUp() }
            )
        }
        composable(DestinasiDetail.routeWithArgs) {
            DetailScreen(
                navigateBack = { navController.navigateUp() },
                navigateToEdit = { nama ->
                    navController.navigate("${DestinasiEdit.route}/$nama")
                }
            )
        }
        composable(DestinasiEdit.routeWithArgs) {
            ItemEditScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}