package com.aldeshov.youtube.ui.activity.main.contents.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.aldeshov.youtube.BASE_URL
import com.aldeshov.youtube.DATE_PATTERN
import com.aldeshov.youtube.R
import com.aldeshov.youtube.service.models.api.content.VideoContent
import com.aldeshov.youtube.ui.templates.ExpandablePreviewCard
import com.aldeshov.youtube.ui.templates.ProfileViewCard
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ContentPreviewAndTitle(title: String, preview: String, isFavourite: Boolean) {
    Column(modifier = Modifier.padding(12.dp)) {
        Card(
            modifier = Modifier
                .height(150.dp)
                .padding(bottom = 10.dp),
            shape = RoundedCornerShape(10.dp),
            elevation = 2.dp
        ) {
            Image(
                painter = rememberImagePainter(
                    data = preview,
                    builder = {
                        placeholder(R.drawable.ic_content_placeholder)
                        error(R.drawable.ic_content_error)
                        crossfade(true)
                    }
                ),
                contentDescription = "Avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            if (isFavourite) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Favourite Text Icon",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
            Text(
                text = title,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight(600),
                color = Color.DarkGray,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Composable
fun ContentViewsAndTime(views: String, createdOn: String) {
    val createdDate = SimpleDateFormat(DATE_PATTERN, Locale.US).parse("$createdOn+0000")

    Column(modifier = Modifier.padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.Visibility,
                contentDescription = "Views",
                tint = Color.DarkGray,
                modifier = Modifier.size(20.dp)
            )

            Text(
                modifier = Modifier.padding(start = 5.dp),
                text = "$views views",
                style = MaterialTheme.typography.body2,
                color = Color.DarkGray,
                fontSize = 12.sp
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.AccessTime,
                contentDescription = "Time",
                tint = Color.DarkGray,
                modifier = Modifier.size(20.dp)
            )

            Text(
                modifier = Modifier.padding(start = 5.dp),
                text = if (createdDate != null) getTimeDifference(
                    createdDate,
                    Date()
                ) else "Unknown time",
                style = MaterialTheme.typography.body2,
                color = Color.DarkGray,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun ContentDescriptionAndLikesDislikes(
    description: String,
    likes: Int,
    dislikes: Int,
    watchAction: () -> Unit
) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(
            text = description,
            color = Color.DarkGray,
            fontWeight = FontWeight(500),
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(horizontal = 12.dp)
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.ThumbUp,
                        contentDescription = "Like",
                        tint = Color(0xBF56E75D),
                        modifier = Modifier.size(20.dp)
                    )

                    Text(
                        modifier = Modifier.padding(start = 5.dp),
                        text = "$likes likes",
                        style = MaterialTheme.typography.body2,
                        color = Color.DarkGray,
                        fontSize = 12.sp
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.ThumbDown,
                        contentDescription = "Dislike",
                        tint = Color(0xBFDA2C2C),
                        modifier = Modifier.size(20.dp)
                    )

                    Text(
                        modifier = Modifier.padding(start = 5.dp),
                        text = "$dislikes dislikes",
                        style = MaterialTheme.typography.body2,
                        color = Color.DarkGray,
                        fontSize = 12.sp
                    )
                }
            }

            Button(
                onClick = watchAction,
                content = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_play),
                            contentDescription = "Play"
                        )

                        Spacer(modifier = Modifier.padding(4.dp))

                        Text("Watch", color = Color.White)
                    }
                }
            )
        }
    }
}

@Composable
fun Content(
    videoContent: VideoContent,
    isSelected: Boolean,
    onClick: () -> Unit,
    watchAction: (code: String) -> Unit,
    isFavourite: Boolean
) {
    ExpandablePreviewCard(expand = isSelected, onClick = onClick) {
        Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            ContentPreviewAndTitle(
                title = videoContent.title,
                preview = "$BASE_URL${videoContent.preview}",
                isFavourite = isFavourite
            )
        }

        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.weight(1F)) {
                ProfileViewCard(
                    videoContent.on_channel.name,
                    "${videoContent.on_channel.subscribers} subscribers",
                    "$BASE_URL${videoContent.on_channel.avatar}"
                ) {}
            }

            Box(modifier = Modifier.weight(1F)) {
                ContentViewsAndTime(videoContent.views, videoContent.created_on)
            }
        }

        if (isSelected) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .padding(bottom = 14.dp)
            ) {
                ContentDescriptionAndLikesDislikes(
                    description = videoContent.description,
                    likes = videoContent.likes,
                    dislikes = videoContent.dislikes,
                    watchAction = { watchAction(videoContent.code) }
                )
            }
        }
    }
}

@Composable
private fun getTimeDifference(before: Date, after: Date): String {
    val timeDifference = after.time - before.time
    if (timeDifference < 1000) {
        return stringResource(R.string.just_now)
    }

    val seconds = timeDifference / 1000
    if (seconds < 60) {
        return if (seconds == 1L) {
            stringResource(R.string.one_second_ago)
        } else {
            stringResource(R.string.x_seconds_ago, seconds)
        }
    }

    val minutes = seconds / 60
    if (minutes < 60) {
        return if (minutes == 1L) {
            stringResource(R.string.one_minute_ago)
        } else {
            stringResource(R.string.x_minutes_ago, minutes)
        }
    }

    val hours = minutes / 60
    if (hours < 24) {
        return if (hours == 1L) {
            stringResource(R.string.one_hour_ago)
        } else {
            stringResource(R.string.x_hours_ago, hours)
        }
    }

    val days = hours / 24
    if (days < 31) {
        return if (days == 1L) {
            stringResource(R.string.one_day_ago)
        } else {
            stringResource(R.string.x_days_ago, days)
        }
    }

    val months = days / 30
    if (months < 12) {
        return if (months == 1L) {
            stringResource(R.string.one_month_ago)
        } else {
            stringResource(R.string.x_months_ago, months)
        }
    }

    val years = months / 12
    return if (years == 1L) {
        stringResource(R.string.one_year_ago)
    } else {
        stringResource(R.string.x_years_ago, years)
    }
}