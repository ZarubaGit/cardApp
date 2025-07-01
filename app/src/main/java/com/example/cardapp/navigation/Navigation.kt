package com.example.cardapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.cardapp.presentation.history.HistoryScreen
import com.example.cardapp.presentation.search.BinSearchScreen
import com.example.cardapp.presentation.detail.BinDetailScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController()
) {
    // Состояние для предотвращения множественных кликов
    var isNavigating by remember { mutableStateOf(false) }
    
    // Безопасная функция навигации с debounce
    val safeNavigate: (String) -> Unit = { route ->
        if (!isNavigating) {
            isNavigating = true
            navController.navigate(route)
            // Сбрасываем флаг через 500ms (время анимации)
            MainScope().launch {
                delay(500)
                isNavigating = false
            }
        }
    }
    
    // Безопасная функция для возврата назад
    val safePopBack: () -> Unit = {
        if (!isNavigating) {
            isNavigating = true
            navController.popBackStack()
            // Сбрасываем флаг через 500ms
            MainScope().launch {
                delay(500)
                isNavigating = false
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Search.route
    ) {
        composable(Screen.Search.route) {
            // Сбрасываем флаг при открытии экрана
            isNavigating = false
            BinSearchScreen(
                onNavigateToHistory = {
                    safeNavigate(Screen.History.route)
                }
            )
        }
        
        composable(Screen.History.route) {
            // Сбрасываем флаг при открытии экрана
            isNavigating = false
            HistoryScreen(
                onNavigateBack = safePopBack,
                onNavigateToDetail = { bin -> safeNavigate(Screen.Detail.createRoute(bin)) }
            )
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("bin") { type = NavType.StringType })
        ) { backStackEntry ->
            isNavigating = false
            val bin = backStackEntry.arguments?.getString("bin") ?: ""
            BinDetailScreen(bin = bin, onBack = safePopBack)
        }
    }
}

sealed class Screen(val route: String) {
    object Search : Screen("search")
    object History : Screen("history")
    object Detail : Screen("detail/{bin}") {
        fun createRoute(bin: String) = "detail/$bin"
    }
} 