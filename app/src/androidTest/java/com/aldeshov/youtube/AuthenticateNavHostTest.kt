package com.aldeshov.youtube

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.aldeshov.youtube.ui.activity.authenticate.AuthenticateNavigation
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.annotation.KoinInternalApi

class AuthenticateNavHostTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private lateinit var navController: NavHostController

    @KoinInternalApi
    @Before
    fun setupAuthenticateNavHost() {
        composeTestRule.setContent {
            navController = rememberNavController()
            AuthenticateNavigation()
        }
    }

    @Test
    fun authenticateNavHost() {
        composeTestRule
            .onNodeWithContentDescription("Email")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription("Password")
            .assertIsDisplayed()
    }

    @Test
    fun createOneTest() {
        composeTestRule.onNodeWithText("Create one!").performClick()

        composeTestRule.onNodeWithText("Sign up").apply {
            performScrollTo()
            performClick()
        }
        composeTestRule.onNodeWithText("Please enter first name").assertIsDisplayed()

        val route = navController.currentBackStackEntry?.destination?.route
        assertEquals(route, "sign_up")
    }
}