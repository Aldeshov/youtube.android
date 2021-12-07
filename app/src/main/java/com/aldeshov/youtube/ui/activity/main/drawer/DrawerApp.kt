package com.aldeshov.youtube.ui.activity.main.drawer

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.aldeshov.youtube.ui.activity.main.drawer.views.Drawer
import com.aldeshov.youtube.ui.activity.main.drawer.views.EmptyDrawer
import com.aldeshov.youtube.ui.templates.DefaultViewModelScreen
import org.koin.android.compat.ScopeCompat
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.context.GlobalContext

@KoinInternalApi
@Composable
fun DrawerApp() {
    val drawerViewModel = ScopeCompat.getViewModel(
        clazz = DrawerViewModel::class.java,
        owner = LocalViewModelStoreOwner.current!!,
        scope = GlobalContext.get().scopeRegistry.rootScope
    )

    DefaultViewModelScreen(
        viewModel = drawerViewModel,
        toLoad = drawerViewModel::getMyChannel,
        emptyText = "There is no channel",
        showWarningIcon = false,
        emptyWidget = { EmptyDrawer() }
    ) {
        Drawer(
            channel = drawerViewModel.channel
        )
    }
}