package com.aldeshov.youtube.ui.activity.main

import android.content.Intent
import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.aldeshov.youtube.R
import com.aldeshov.youtube.service.models.LiveStatus
import com.aldeshov.youtube.ui.activity.authenticate.AuthenticateActivity
import com.aldeshov.youtube.ui.activity.main.account.AccountApp
import com.aldeshov.youtube.ui.activity.main.content.ContentApp
import com.aldeshov.youtube.ui.activity.main.contents.ListApp
import com.aldeshov.youtube.ui.templates.CenterScreenBox
import com.aldeshov.youtube.ui.templates.ErrorWidget
import com.aldeshov.youtube.ui.templates.TopScreenBox
import com.aldeshov.youtube.ui.templates.WarningWidget
import com.aldeshov.youtube.ui.theme.YouTubeTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.compat.ScopeCompat.getViewModel
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.context.GlobalContext

@KoinInternalApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YouTubeTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun SplashScreen(loadData: () -> Unit) {
    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }

    // Animation
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f,
            // tween Animation
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                })
        )

        delay(2000L)
        loadData()
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher),
            contentDescription = "Logo",
            modifier = Modifier.scale(scale.value)
        )
    }
}

@KoinInternalApi
@Composable
fun MainNavigation(
    innerPadding: PaddingValues,
    navController: NavHostController,
    setCurrentNavigationPage: (index: Int) -> Unit,
    setToHideBottomAppBar: (value: Boolean) -> Unit
) {
    NavHost(
        modifier = Modifier.padding(innerPadding),
        navController = navController,
        startDestination = MainNavigationItem.Home.route
    ) {
        // List: all Video Contents
        composable(MainNavigationItem.Home.route) {
            ListApp(watchAction = { navigateToVideo(navController = navController, code = it) })
            setCurrentNavigationPage(MainNavigationItem.Home.index)
            setToHideBottomAppBar(false)
        }

        // List: favourite Video Contents
        composable(MainNavigationItem.Favourite.route) {
            ListApp(
                watchAction = { navigateToVideo(navController = navController, code = it) },
                isFavourite = true
            )
            setCurrentNavigationPage(MainNavigationItem.Favourite.index)
            setToHideBottomAppBar(false)
        }

        // Watch: Video Content
        composable(
            route = "video/{code}",
            arguments = listOf(
                navArgument("code") {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            entry.arguments?.getString("code")?.let {
                ContentApp(code = it)
                setToHideBottomAppBar(true)
            }
        }

        // Show: User information
        composable(MainNavigationItem.Account.route) {
            AccountApp()
            setCurrentNavigationPage(MainNavigationItem.Account.index)
            setToHideBottomAppBar(false)
        }
    }
}

@KoinInternalApi
@Composable
fun MainApp(mainViewModel: MainViewModel) {
    val placeholderTextForDecorativeButton =
        stringResource(id = R.string.button_decorative_placeholder)
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val navPages = listOf(
        MainNavigationItem.Home,
        MainNavigationItem.Favourite,
        MainNavigationItem.Account,
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "YouTube",
                        fontWeight = FontWeight(600)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Menu Button"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        scope.launch {
                            scaffoldState
                                .snackbarHostState
                                .showSnackbar(
                                    message = placeholderTextForDecorativeButton,
                                    actionLabel = "Okay",
                                    duration = SnackbarDuration.Short
                                )
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            tint = MaterialTheme.colors.onPrimary,
                            contentDescription = "Settings Button"
                        )
                    }
                },
                backgroundColor = Color.White,
                contentColor = Color.DarkGray,
                elevation = 2.dp
            )
        },
        bottomBar = {
            if (!mainViewModel.hideBottomAppBar) {
                BottomNavigation(
                    elevation = 5.dp,
                    backgroundColor = Color.White
                ) {
                    navPages.forEach { item ->
                        BottomNavigationItem(
                            icon = { Icon(item.icon, contentDescription = item.title) },
                            label = { Text(item.title) },
                            selected = mainViewModel.currentPageIndex == item.index,
                            onClick = {
                                mainViewModel.navigateTo(
                                    mainNavigation = item,
                                    navController = navController
                                )
                            },
                            alwaysShowLabel = false,
                            selectedContentColor = MaterialTheme.colors.primary,
                            unselectedContentColor = MaterialTheme.colors.onPrimary
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        MainNavigation(
            innerPadding = innerPadding,
            navController = navController,
            setCurrentNavigationPage = mainViewModel::setCurrentPage,
            setToHideBottomAppBar = mainViewModel::setToHideBottomAppBar
        )
    }
}

@KoinInternalApi
@Composable
fun MainScreen() {
    val context = LocalContext.current
    val mainViewModel = getViewModel(
        clazz = MainViewModel::class.java,
        owner = LocalViewModelStoreOwner.current!!,
        scope = GlobalContext.get().scopeRegistry.rootScope
    )

    when (mainViewModel.status) {
        LiveStatus.NOTHING, LiveStatus.LOADING -> {
            SplashScreen(mainViewModel::loadData)
        }
        LiveStatus.LOADED_EMPTY -> {
            context.startActivity(Intent(context, AuthenticateActivity::class.java))
            (context as ComponentActivity).finish()
        }
        LiveStatus.LOADED_SUCCESSFUL -> {
            MainApp(mainViewModel)
        }
        LiveStatus.ERROR -> {
            TopScreenBox {
                ErrorWidget(message = mainViewModel.message)
            }

            CenterScreenBox {
                WarningWidget()
            }
        }
    }
}

private fun navigateToVideo(navController: NavHostController, code: String) {
    navController.navigate("video/$code") {
        launchSingleTop = true
        restoreState = true
    }
}