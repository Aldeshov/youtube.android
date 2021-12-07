package com.aldeshov.youtube.ui.templates

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aldeshov.youtube.service.models.LiveStatus
import com.aldeshov.youtube.service.models.base.BaseComposeViewModel
import com.aldeshov.youtube.ui.activity.main.contents.views.ContentList

@Composable
fun ScrollableFormBox(
    scrollState: ScrollState = rememberScrollState(),
    body: @Composable () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Card(
            elevation = 2.dp,
            shape = AbsoluteRoundedCornerShape(8.dp),
            backgroundColor = MaterialTheme.colors.surface,
            modifier = Modifier.padding(24.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(8.dp)
            ) {
                body()
            }
        }
    }
}

@Composable
fun CenterScreenBox(body: @Composable () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        body()
    }
}

@Composable
fun TopScreenBox(body: @Composable () -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopCenter,
    ) {
        body()
    }
}

@Composable
fun DefaultViewModelScreen(
    viewModel: BaseComposeViewModel,
    toLoad: () -> Unit,
    emptyText: String,
    showWarningIcon: Boolean = true,
    onSuccessful: @Composable () -> Unit
) {
    if (viewModel.status == LiveStatus.NOTHING)
        toLoad()

    if (viewModel.status == LiveStatus.LOADING) {
        CenterScreenBox {
            LoadingWidget()
        }
    }

    if (viewModel.status == LiveStatus.ERROR) {
        TopScreenBox {
            ErrorWidget(message = viewModel.message)
        }
        if (showWarningIcon) {
            CenterScreenBox {
                WarningWidget()
            }
        }
    }

    if (viewModel.status == LiveStatus.LOADED_EMPTY) {
        CenterScreenBox {
            EmptyWidget(emptyText)
        }
    }

    if (viewModel.status == LiveStatus.LOADED_SUCCESSFUL)
        onSuccessful()
}