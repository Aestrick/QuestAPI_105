package com.example.pertemuan12.uicontroller

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pertemuan12.uicontroller.route.DestinasiDetail
import com.example.pertemuan12.uicontroller.route.DestinasiEdit
import com.example.pertemuan12.uicontroller.route.DestinasiEntry
import com.example.pertemuan12.uicontroller.route.DestinasiHome
import com.example.pertemuan12.view.DetailScreen
import com.example.pertemuan12.view.EditSiswaScreen
import com.example.pertemuan12.view.EntrySiswaScreen
import com.example.pertemuan12.view.HomeScreen

@Composable
fun DataSiswaApp(
    navController: NavHostController = rememberNavController(),
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
                onDetailClick = { itemId ->
                    navController.navigate("${DestinasiDetail.route}/$itemId")
                }
            )
        }
        composable(DestinasiEntry.route) {
            EntrySiswaScreen(
                navigateBack = { navController.popBackStack() }
            )
        }
        composable(
            route = DestinasiDetail.routeWithArgs,
            arguments = listOf(navArgument(DestinasiDetail.itemIdArg) {
                type = NavType.IntType
            })
        ) {
            DetailScreen(
                navigateBack = { navController.navigateUp() },
                onEditClick = { itemId ->
                    navController.navigate("${DestinasiEdit.route}/$itemId")
                }
            )
        }
        composable(
            route = DestinasiEdit.routeWithArgs,
            arguments = listOf(navArgument(DestinasiEdit.itemIdArg) {
                type = NavType.IntType
            })
        ) {
            EditSiswaScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}