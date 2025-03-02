sealed class Screen(val route: String) {
    object SplashScreen : Screen("splash_screen")
    object WelcomeScreen : Screen("welcome_screen")
    object LoginScreen : Screen("login_screen")
    object SignUpScreen : Screen("sign_up_screen")
    object ProductListScreen : Screen("product_list_screen")
    object MainScreen : Screen("main_screen")
    object ProductDetailsScreen : Screen("product_details/{productId}") {
        fun createRoute(productId: Int): String = "product_details/$productId"
    }

}