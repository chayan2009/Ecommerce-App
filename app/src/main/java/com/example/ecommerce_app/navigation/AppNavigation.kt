package com.example.ecommerce_app.navigation

import Screen
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.ecommerce_app.feature_login.LoginScreen
import com.example.ecommerce_app.feature_signup.presentation.SignUpScreen
import com.example.ecommerce_app.feature_splash.SplashScreen
import com.example.ecommerce_app.feature_welcome.WelcomeScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigation(navController: NavHostController) {

    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ) {
        // Common transition for all screens
        val enterTransition: EnterTransition = slideInHorizontally(
            initialOffsetX = { 1000 }, animationSpec = tween(500)
        )

        val exitTransition: ExitTransition = slideOutHorizontally(
            targetOffsetX = { -1000 }, animationSpec = tween(500)
        )

        val popEnterTransition: EnterTransition = slideInHorizontally(
            initialOffsetX = { -1000 }, animationSpec = tween(500)
        )

        val popExitTransition: ExitTransition = slideOutHorizontally(
            targetOffsetX = { 1000 }, animationSpec = tween(500)
        )

        // Splash Screen
        composable(
            route = Screen.SplashScreen.route,
            enterTransition = { enterTransition },
            exitTransition = { exitTransition },
            popEnterTransition = { popEnterTransition },
            popExitTransition = { popExitTransition }
        ) {
            SplashScreen(navController, hiltViewModel())
        }

        // Welcome Screen
        composable(
            route = Screen.WelcomeScreen.route,
            enterTransition = { enterTransition },
            exitTransition = { exitTransition },
            popEnterTransition = { popEnterTransition },
            popExitTransition = { popExitTransition }
        ) {
            WelcomeScreen(navController)
        }

        // Login Screen
        composable(
            route = Screen.LoginScreen.route,
            enterTransition = { enterTransition },
            exitTransition = { exitTransition },
            popEnterTransition = { popEnterTransition },
            popExitTransition = { popExitTransition }
        ) {
            LoginScreen(navController)
        }

        // SignUp Screen
        composable(
            route = Screen.SignUpScreen.route,
            enterTransition = { enterTransition },
            exitTransition = { exitTransition },
            popEnterTransition = { popEnterTransition },
            popExitTransition = { popExitTransition }
        ) {
            SignUpScreen(navController)
        }

        // Main Screen
        composable(
            route = Screen.MainScreen.route,
            enterTransition = { enterTransition },
            exitTransition = { exitTransition },
            popEnterTransition = { popEnterTransition },
            popExitTransition = { popExitTransition }
        ) {
            MainScreen(navController)
        }

    }
}
