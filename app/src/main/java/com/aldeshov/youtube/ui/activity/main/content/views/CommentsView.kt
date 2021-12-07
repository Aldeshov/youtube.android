package com.aldeshov.youtube.ui.activity.main.content.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.aldeshov.youtube.R
import com.aldeshov.youtube.service.models.LiveStatus

@Composable
fun CommentInput(
    status: LiveStatus,
    comment: TextFieldValue,
    updateComment: (comment: TextFieldValue) -> Unit,
    sendAction: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(12.dp)
    ) {
        Box(modifier = Modifier.weight(1F)) {
            TextField(
                value = comment,
                onValueChange = updateComment,
                enabled = status != LiveStatus.LOADING,
                label = { Text("Comment", color = MaterialTheme.colors.onPrimary) },
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Send,
                        contentDescription = "Comment",
                    )
                }
            )
        }

        OutlinedButton(
            onClick = sendAction,
            enabled = status != LiveStatus.LOADING,
            modifier = Modifier.padding(horizontal = 10.dp),
        ) {
            Text(text = "Send", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun Comment(text: String, channelName: String, channelAvatar: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {})
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier.size(48.dp),
            shape = CircleShape,
            elevation = 2.dp
        ) {
            Image(
                painter = rememberImagePainter(
                    data = channelAvatar,
                    builder = {
                        placeholder(R.drawable.ic_placeholder)
                        error(R.drawable.ic_error)
                        crossfade(true)
                    }
                ),
                contentDescription = "Avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Column(modifier = Modifier.padding(start = 8.dp)) {
            Text(
                maxLines = 1,
                text = channelName,
                style = MaterialTheme.typography.body2,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis
            )

            // LocalContentAlpha is defining opacity level of its children
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}