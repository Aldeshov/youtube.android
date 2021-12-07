package com.aldeshov.youtube.ui.activity.main.account

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.aldeshov.youtube.ui.activity.main.account.views.Account
import com.aldeshov.youtube.ui.templates.DefaultViewModelScreen
import org.koin.android.compat.ScopeCompat.getViewModel
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.context.GlobalContext

@KoinInternalApi
@Composable
fun AccountApp() {
    val context = LocalContext.current

    val accountViewModel = getViewModel(
        clazz = AccountViewModel::class.java,
        owner = LocalViewModelStoreOwner.current!!,
        scope = GlobalContext.get().scopeRegistry.rootScope
    )

    val profileViewModel = getViewModel(
        clazz = ProfileViewModel::class.java,
        owner = LocalViewModelStoreOwner.current!!,
        scope = GlobalContext.get().scopeRegistry.rootScope
    )

    DefaultViewModelScreen(
        viewModel = accountViewModel,
        toLoad = { accountViewModel.getLocalUser() },
        emptyText = "There is no account"
    ) {
        Account(
            accountViewModel = accountViewModel,
            profileViewModel = profileViewModel
        ) {
            accountViewModel.logout(context)
        }
    }
}