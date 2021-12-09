package com.aldeshov.youtube

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.aldeshov.youtube.ui.activity.authenticate.AuthenticateNavigation
import com.aldeshov.youtube.ui.theme.YouTubeTheme
import org.junit.Rule
import org.junit.Test
import org.koin.core.annotation.KoinInternalApi

@KoinInternalApi
class AuthenticateAppTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    // use createAndroidComposeRule<YourActivity>() if you need access to
    // an activity

    @Test
    fun loginAppWidgetsTest() {
        // Start the app
        composeTestRule.setContent {
            YouTubeTheme {
                AuthenticateNavigation()
            }
        }

        composeTestRule.onNodeWithText("Email").assertExists("Email does not exist")
        composeTestRule.onNodeWithText("Password").assertExists("Password does not exist")
        composeTestRule.onNodeWithText("Log in").performClick()
        composeTestRule.onNodeWithText("Please enter email address").assertIsDisplayed()
    }

    @Test
    fun signupAppWidgetsTest() {
        // Start the app
        composeTestRule.setContent {
            YouTubeTheme {
                AuthenticateNavigation()
            }
        }
        composeTestRule.onNodeWithText("Create one!").performClick()

        composeTestRule.onNodeWithText("First name").assertExists("First name does not exist")
        composeTestRule.onNodeWithText("Last name").assertExists("Last name does not exist")
        composeTestRule.onNodeWithText("Email").assertExists("Email does not exist")
        composeTestRule.onNodeWithText("Password").assertExists("Password does not exist")
        composeTestRule.onNodeWithText("Confirm password").assertExists("Confirm password does not exist")

        composeTestRule.onNodeWithText("Sign up").performClick()
        composeTestRule.onNodeWithText("Please enter first name").assertIsDisplayed()
    }
}