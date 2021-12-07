package com.aldeshov.youtube.ui.activity.main.content

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.aldeshov.youtube.ui.activity.main.content.views.VideoContent
import com.aldeshov.youtube.ui.templates.DefaultViewModelScreen
import org.koin.android.compat.ScopeCompat.getViewModel
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.context.GlobalContext

@KoinInternalApi
@Composable
fun ContentApp(code: String) {
    val contentViewModel = getViewModel(
        clazz = ContentViewModel::class.java,
        owner = LocalViewModelStoreOwner.current!!,
        scope = GlobalContext.get().scopeRegistry.rootScope
    )

    val commentsViewModel = getViewModel(
        clazz = CommentsViewModel::class.java,
        owner = LocalViewModelStoreOwner.current!!,
        scope = GlobalContext.get().scopeRegistry.rootScope
    )

    DefaultViewModelScreen(
        viewModel = contentViewModel,
        toLoad = { contentViewModel.fetchContent(code) },
        emptyText = "No video content"
    ) {
        commentsViewModel.videoContentCode = contentViewModel.videoContent.code

        VideoContent(
            contentViewModel = contentViewModel,
            commentsViewModel = commentsViewModel
        )
    }
}