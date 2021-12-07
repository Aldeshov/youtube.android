package com.aldeshov.youtube.ui.activity.authenticate

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aldeshov.youtube.R
import com.aldeshov.youtube.ui.activity.authenticate.login.LoginApp
import com.aldeshov.youtube.ui.activity.authenticate.login.LoginViewModel
import com.aldeshov.youtube.ui.activity.authenticate.signup.SignupApp
import com.aldeshov.youtube.ui.activity.authenticate.signup.SignupViewModel
import com.aldeshov.youtube.ui.theme.YouTubeTheme
import org.koin.android.compat.ScopeCompat
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.context.GlobalContext

@KoinInternalApi
class AuthenticateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YouTubeTheme {
                AuthenticateNavigation()
            }
        }
    }
}

@KoinInternalApi
@Composable
fun AuthenticateNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login_page"
    ) {
        composable("login_page") {
            LoginApp(
                loginViewModel = ScopeCompat.getViewModel(
                    clazz = LoginViewModel::class.java,
                    owner = LocalViewModelStoreOwner.current!!,
                    scope = GlobalContext.get().scopeRegistry.rootScope
                ),
                signupAction = {
                    navController.navigate("signup_page")
                }
            )
        }

        composable("signup_page") {
            val context = LocalContext.current
            val signedUpText = stringResource(id = R.string.successfully_signed_in_text)
            SignupApp(
                signupViewModel = ScopeCompat.getViewModel(
                    clazz = SignupViewModel::class.java,
                    owner = LocalViewModelStoreOwner.current!!,
                    scope = GlobalContext.get().scopeRegistry.rootScope
                ),
                signedUpAction = {
                    Toast.makeText(context, signedUpText, Toast.LENGTH_LONG).show()
                    navController.popBackStack("signup_page", true)
                }
            )
        }
    }
}