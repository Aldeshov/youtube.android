package com.aldeshov.youtube

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.aldeshov.youtube.service.models.navigation.MainNavigationItem
import com.aldeshov.youtube.ui.activity.main.MainNavigation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.annotation.KoinInternalApi

class MainNavHostTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    lateinit var navController: NavHostController

    @KoinInternalApi
    @Before
    fun setupMainNavHost() {
        composeTestRule.setContent {
            navController = rememberNavController()
            MainNavigation(innerPadding = PaddingValues(0.dp),navController = navController, {}) {}
        }
    }

    @Test
    fun mainNavHost() {
        composeTestRule
            .onNode(SemanticsMatcher(description = "Loading", matcher = {true}))
            .assertIsDisplayed()
    }

    @Test
    fun navigateToAccountPage() {
        runBlocking {
            withContext(Dispatchers.Main) {
                navController.navigate(MainNavigationItem.Account.route)
            }
        }
        composeTestRule
            .onNodeWithContentDescription("User Avatar")
            .assertIsDisplayed()
    }
}