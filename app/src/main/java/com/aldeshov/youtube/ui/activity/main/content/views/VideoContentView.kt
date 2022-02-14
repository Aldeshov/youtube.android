package com.aldeshov.youtube.ui.activity.main.content.views

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.ThumbDown
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.aldeshov.youtube.BASE_URL
import com.aldeshov.youtube.service.models.LiveStatus
import com.aldeshov.youtube.service.models.api.applications.SubscribeStatusType
import com.aldeshov.youtube.service.models.api.content.LikeStatusType
import com.aldeshov.youtube.ui.activity.main.content.CommentsViewModel
import com.aldeshov.youtube.ui.activity.main.content.ContentViewModel
import com.aldeshov.youtube.ui.activity.main.contents.views.ContentViewsAndTime
import com.aldeshov.youtube.ui.templates.DefaultViewModelScreen
import com.aldeshov.youtube.ui.templates.ProfileViewCard
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView

@Composable
fun VideoContentTitleAndDescription(title: String, description: String) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight(600),
            color = Color.DarkGray,
            modifier = Modifier.padding(8.dp)
        )

        Text(
            text = description,
            style = MaterialTheme.typography.body2,
            fontWeight = FontWeight(400),
            color = Color.Gray,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun VideoContentInfo(
    likes: Int,
    dislikes: Int,
    likeStatus: LikeStatusType,
    views: String,
    createdOn: String,
    likeAction: () -> Unit,
    disLikeAction: () -> Unit,
    fetchLikeStatus: () -> Unit,
    allLikesDislikes: Float = (likes + dislikes).toFloat()
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(vertical = 8.dp)
                .weight(2F)
        ) {
            LinearProgressIndicator(
                modifier = Modifier.height(2.dp),
                progress =
                if (allLikesDislikes == 0F)
                    0.5F
                else
                    likes / allLikesDislikes,
                color = Color.Gray,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                if (likeStatus == LikeStatusType.NOTHING)
                    fetchLikeStatus()
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .clickable(
                            enabled = likeStatus != LikeStatusType.FETCHING,
                            onClick = likeAction
                        )
                        .padding(10.dp)
                ) {
                    Icon(
                        imageVector =
                        if (likeStatus == LikeStatusType.LIKED)
                            Icons.Filled.ThumbUp
                        else
                            Icons.Outlined.ThumbUp,
                        contentDescription = "Like",
                        modifier = Modifier
                            .padding(end = 5.dp)
                            .size(20.dp)
                    )

                    Text(
                        text = "$likes likes",
                        style = MaterialTheme.typography.body2,
                        color = Color.DarkGray,
                        fontSize = 12.sp
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .padding(start = 5.dp)
                        .clickable(
                            enabled = likeStatus != LikeStatusType.FETCHING,
                            onClick = disLikeAction
                        )
                        .padding(10.dp)
                ) {
                    Icon(
                        imageVector =
                        if (likeStatus == LikeStatusType.DISLIKED)
                            Icons.Filled.ThumbDown
                        else
                            Icons.Outlined.ThumbDown,
                        contentDescription = "Dislike",
                        modifier = Modifier
                            .padding(end = 5.dp)
                            .size(20.dp)
                    )

                    Text(
                        text = "$dislikes dislikes",
                        style = MaterialTheme.typography.body2,
                        color = Color.DarkGray,
                        fontSize = 12.sp
                    )
                }
            }
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.weight(1F)
        ) {
            ContentViewsAndTime(views = views, createdOn = createdOn)
        }
    }
}

@Composable
fun VideoContentChannel(
    subscribers: Int,
    subscribeStatus: SubscribeStatusType,
    channelName: String,
    channelAvatar: String,
    subscribeAction: () -> Unit,
    unsubscribeAction: () -> Unit,
    fetchSubscribeStatus: () -> Unit,
) {
    val confirmUnsubscribe = remember { mutableStateOf(false) }

    if (confirmUnsubscribe.value) {
        AlertDialog(
            title = { Text(text = "Unsubscribe") },
            text = {
                Text(text = "Do you want to unsubscribe from channel $channelName")
            },
            onDismissRequest = { confirmUnsubscribe.value = false },
            confirmButton = {
                TextButton(onClick = { unsubscribeAction(); confirmUnsubscribe.value = false }) {
                    Text(text = "Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = { confirmUnsubscribe.value = false }) {
                    Text(text = "No")
                }
            }
        )
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(12.dp)
    ) {
        if (subscribeStatus == SubscribeStatusType.NOTHING)
            fetchSubscribeStatus()

        Box(modifier = Modifier.weight(1F)) {
            ProfileViewCard(
                title = channelName,
                subText = "$subscribers subscribers",
                imageURL = "$BASE_URL${channelAvatar}"
            ) {}
        }

        Button(
            enabled = subscribeStatus != SubscribeStatusType.FETCHING,
            modifier = Modifier.padding(start = 16.dp, end = 8.dp),
            onClick = {
                if (subscribeStatus == SubscribeStatusType.SUBSCRIBED)
                    confirmUnsubscribe.value = true
                else
                    subscribeAction()
            }
        ) {
            Text(
                text = if (subscribeStatus == SubscribeStatusType.SUBSCRIBED) "Unsubscribe" else "Subscribe",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun VideoContent(contentViewModel: ContentViewModel, commentsViewModel: CommentsViewModel) {
    val context = LocalContext.current
    val scrollState = rememberLazyListState()
    val uri = "$BASE_URL${contentViewModel.videoContent.video}"
    Log.e("CHECK", uri)
    val exoPlayer = remember(contentViewModel) {
        ExoPlayer.Builder(context).build().also { exoPlayer ->
            exoPlayer.setMediaItem(MediaItem.fromUri(uri))
            exoPlayer.prepare()
        }
    }

    Column {
        // Video Container
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
            factory = {
                PlayerView(it).apply {
                    player = exoPlayer
                }
            }
        )

        LazyColumn(state = scrollState) {
            item {
                VideoContentTitleAndDescription(
                    title = contentViewModel.videoContent.title,
                    description = contentViewModel.videoContent.description
                )

                VideoContentInfo(
                    likes = contentViewModel.floatingLikes,
                    dislikes = contentViewModel.floatingDislikes,
                    likeStatus = contentViewModel.likeStatus,
                    views = contentViewModel.videoContent.views,
                    createdOn = contentViewModel.videoContent.created_on,
                    likeAction = contentViewModel::likeContent,
                    disLikeAction = contentViewModel::dislikeContent,
                    fetchLikeStatus = contentViewModel::fetchLikeStatus
                )

                Divider(
                    thickness = 1.dp,
                    color = Color(0xCCDDDDDD)
                )

                VideoContentChannel(
                    subscribers = contentViewModel.floatingSubscribers,
                    subscribeStatus = contentViewModel.subscribeStatus,
                    channelName = contentViewModel.videoContent.on_channel.name,
                    channelAvatar = contentViewModel.videoContent.on_channel.avatar,
                    subscribeAction = contentViewModel::subscribeChannel,
                    unsubscribeAction = contentViewModel::unsubscribeChannel,
                    fetchSubscribeStatus = contentViewModel::fetchSubscribeStatus
                )

                Divider(
                    thickness = 1.dp,
                    color = Color(0xCCDDDDDD)
                )

                CommentInput(
                    status = commentsViewModel.status,
                    comment = commentsViewModel.comment,
                    updateComment = commentsViewModel::updateComment,
                    sendAction = { commentsViewModel.sendComment() }
                )

                Divider(
                    thickness = 1.dp,
                    color = Color(0xCCDDDDDD)
                )
            }

            item {
                DefaultViewModelScreen(
                    viewModel = commentsViewModel,
                    toLoad = { commentsViewModel.fetchComments() },
                    emptyText = "No comments yet",
                    showWarningIcon = false
                ) {
                    Text(
                        text = "Comments: ${commentsViewModel.comments.size}",
                        Modifier
                            .padding(12.dp)
                            .padding(top = 8.dp)
                    )
                }
            }

            if (commentsViewModel.status == LiveStatus.LOADED_SUCCESSFUL) {
                items(items = commentsViewModel.comments) {
                    Comment(
                        text = it.text,
                        channelName = it.owner.name,
                        channelAvatar = "$BASE_URL${it.owner.avatar}",
                    )

                    Divider(
                        thickness = 1.dp,
                        color = Color(0xABDDDDDD)
                    )
                }
            }
        }
    }
}