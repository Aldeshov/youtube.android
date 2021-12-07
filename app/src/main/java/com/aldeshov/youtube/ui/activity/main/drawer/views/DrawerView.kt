package com.aldeshov.youtube.ui.activity.main.drawer.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.aldeshov.youtube.BASE_URL
import com.aldeshov.youtube.DATE_PATTERN
import com.aldeshov.youtube.R
import com.aldeshov.youtube.service.models.api.applications.Channel
import com.aldeshov.youtube.ui.templates.PropertyWidget
import com.aldeshov.youtube.ui.templates.TopScreenBox
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun Drawer(channel: Channel) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = Modifier.size(96.dp),
                shape = CircleShape,
                elevation = 3.dp
            ) {
                Image(
                    painter = rememberImagePainter(
                        data = "$BASE_URL${channel.avatar}",
                        builder = {
                            crossfade(true)
                            placeholder(R.drawable.ic_placeholder)
                            error(R.drawable.ic_error)
                        }
                    ),
                    contentDescription = "Channel Avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(
                    text = channel.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = 6.dp),
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold
                )

                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        maxLines = 3,
                        text = channel.description,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.body2,
                    )
                }
            }
        }

        PropertyWidget("Channel code", channel.code, true)

        Divider(color = Color(0xCCC4C4C4), thickness = 1.dp)

        PropertyWidget(
            "Channel verify",
            "This channel is" + (if (channel.is_verified) "" else " not") + " verified",
            false
        )

        Divider(color = Color(0xCCC4C4C4), thickness = 1.dp)

        PropertyWidget(
            "Subscribers",
            "Channel has ${channel.subscribers} subscribers",
            false
        )

        val createdDateLong = SimpleDateFormat(DATE_PATTERN, Locale.US)
            .parse("${channel.created_date}+0000")

        if (createdDateLong != null) {
            Divider(color = Color(0xCCC4C4C4), thickness = 1.dp)

            PropertyWidget(
                "Created date",
                "Channel has been created on ${Date(createdDateLong.time)}",
                false
            )
        }

        Spacer(modifier = Modifier.weight(1F))

        Text(
            text = "YouTube: Developed by Azat",
            color = MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(12.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun EmptyDrawer() {
    Column {
        TopScreenBox {
            Card(
                shape = AbsoluteRoundedCornerShape(5.dp),
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = Color.White,
                elevation = 2.dp,
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    "You do not have a channel",
                    modifier = Modifier.padding(24.dp),
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.weight(1F))

        Text(
            text = "YouTube: Developed by Azat",
            color = MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(12.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}