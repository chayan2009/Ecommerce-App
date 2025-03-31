package com.example.ecommerce_app.core.utils

object NavigationConstants {

    const val HOME_ROUTE = "home_route"
    const val SHOP_ROUTE = "shop_route"
    const val CART_ROUTE = "cart_route"
    const val WISHLIST_ROUTE = "wishlist_route"
    const val PROFILE_ROUTE = "profile_route"

    const val PRODUCT_ID_PARAM = "id"
    const val PRICE_PARAM = "price"
    const val COUNT_PARAM = "count"

    const val EDIT_PROFILE_SCREEN = "edit_profile_screen"
    const val PRODUCT_DETAILS_SCREEN = "product_details/{$PRODUCT_ID_PARAM}"
    const val SUCCESS_SCREEN = "success"
    const val CHECKOUT_SCREEN = "checkout/{$PRICE_PARAM}/{$COUNT_PARAM}"
    const val SETTINGS_SCREEN = "settings"
    const val ORDERS_SCREEN = "orders"
    const val NOTIFICATIONS_SCREEN = "notifications"


    const val SPLASH_SCREEN = "splash_screen"
    const val WELCOME_SCREEN = "welcome_screen"
    const val LOGIN_SCREEN = "login_screen"
    const val SIGN_UPSCREEN = "sign_up_screen"
    const val AUTH_UPSCREEN = "auth"
    const val MAIN_UPSCREEN = "main"
    const val HOME_UPSCREEN = "home"

    fun buildProductDetailsRoute(productId: Int) = "product_details/$productId"
    fun buildFavouriteRoute(productId: Int) = "wishlist/$productId"
    fun buildCheckoutRoute(price: Double, count: Int) = "checkout/$price/$count"
}