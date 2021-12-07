package com.aldeshov.youtube.ui.activity.main.contents

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.aldeshov.youtube.ui.activity.main.contents.views.ContentList
import com.aldeshov.youtube.ui.templates.DefaultViewModelScreen
import org.koin.android.compat.ScopeCompat
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.context.GlobalContext

@KoinInternalApi
@Composable
fun ListApp(watchAction: (code: String) -> Unit, isFavourite: Boolean = false) {
    val listViewModel = ScopeCompat.getViewModel(
        clazz = ListViewModel::class.java,
        owner = LocalViewModelStoreOwner.current!!,
        scope = GlobalContext.get().scopeRegistry.rootScope
    )

    DefaultViewModelScreen(
        viewModel = listViewModel,
        toLoad = {
            if (isFavourite) listViewModel.fetchFavouriteContentList()
            else listViewModel.fetchContentList()
        },
        emptyText = if (isFavourite) "No favourite video contents" else "No video contents",
    ) {
        ContentList(
            list = listViewModel.list,
            currentItem = listViewModel.currentItem,
            setCurrentItem = listViewModel::setCurrentListItem,
            watchAction = watchAction,
            isFavourite = isFavourite
        )
    }
}