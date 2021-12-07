package com.aldeshov.youtube.ui.activity.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import com.aldeshov.youtube.service.models.LiveStatus
import com.aldeshov.youtube.service.models.api.ResponseType
import com.aldeshov.youtube.service.models.base.BaseComposeViewModel
import com.aldeshov.youtube.service.repositories.UserRepository

class MainViewModel(private val userRepository: UserRepository) : BaseComposeViewModel() {
    var currentPageIndex by mutableStateOf(0)
        private set
    var hideBottomAppBar by mutableStateOf(false)
        private set

    fun loadData() {
        status = LiveStatus.LOADING
        userRepository.checkCurrentUser { response, message ->
            when (response) {
                ResponseType.RESPONSE_SUCCESSFUL -> {
                    status = LiveStatus.LOADED_SUCCESSFUL
                }
                ResponseType.RESPONSE_INCORRECT_DATA -> {
                    status = LiveStatus.LOADED_EMPTY
                }
                else -> {
                    status = LiveStatus.ERROR
                    if (message != null)
                        this.message = message
                }
            }
        }
    }

    fun navigateTo(mainNavigation: MainNavigationItem, navController: NavHostController) {
        navController.navigate(mainNavigation.route) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            navController.graph.startDestinationRoute?.let { route ->
                popUpTo(route) {
                    saveState = true
                }
            }

            // Avoid multiple copies of the same destination when
            // re-selecting the same item
            launchSingleTop = true

            // Restore state when re-selecting a previously selected item
            restoreState = true
        }
    }

    fun setCurrentPage(index: Int) {
        currentPageIndex = index
    }

    fun setToHideBottomAppBar(value: Boolean) {
        hideBottomAppBar = value
    }
}