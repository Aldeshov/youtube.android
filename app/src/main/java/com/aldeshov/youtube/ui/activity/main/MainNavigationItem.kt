package com.aldeshov.youtube.ui.activity.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class MainNavigationItem(var index: Int, var route: String, var icon: ImageVector, var title: String) {
    object Home : MainNavigationItem(0, "home_page", Icons.Filled.Home, "Home")
    object Favourite : MainNavigationItem(1, "favourite_page", Icons.Filled.Star, "Favourite")
    object Account : MainNavigationItem(2, "account_page", Icons.Filled.AccountBox, "Account")
}