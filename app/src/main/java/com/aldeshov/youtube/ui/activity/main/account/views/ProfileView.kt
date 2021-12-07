package com.aldeshov.youtube.ui.activity.main.account.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.aldeshov.youtube.R
import com.aldeshov.youtube.service.models.api.authentication.Profile
import com.aldeshov.youtube.service.models.api.content.Playlist
import com.aldeshov.youtube.ui.templates.PropertyWidget

@Composable
fun PlaylistCard(playlist: Playlist) {
    Card(
        elevation = 2.dp,
        shape = AbsoluteRoundedCornerShape(5.dp),
        modifier = Modifier
            .width(185.dp)
            .padding(12.dp),
    ) {
        Column {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(96.dp)
                    .background(MaterialTheme.colors.onPrimary)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_playlist),
                    contentDescription = "Playlist Icon",
                    modifier = Modifier
                        .size(64.dp)
                )
            }

            Text(
                text = playlist.title,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight(600),
                modifier = Modifier.padding(12.dp)
            )

            Text(
                text = "Contents: ${playlist.contents.size}",
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(horizontal = 12.dp)
            )

            IconButton(
                onClick = {},
                modifier = Modifier
                    .padding(12.dp)
                    .shadow(2.dp, CircleShape)
                    .background(MaterialTheme.colors.primary)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_play),
                    contentDescription = "Play Icon",
                    modifier = Modifier
                        .size(28.dp)
                )
            }
        }
    }
}

@Composable
fun Profile(profile: Profile) {
    val scrollState = rememberScrollState()

    Text(
        text = "Profile",
        modifier = Modifier.padding(24.dp),
        style = MaterialTheme.typography.h5,
        fontWeight = FontWeight.Bold
    )

    Row(modifier = Modifier.padding(8.dp)) {
        Icon(
            imageVector = if (profile.is_private) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
            contentDescription = "Privacy Icon",
            modifier = Modifier.padding(end = 8.dp),
            tint = if (profile.is_private) MaterialTheme.colors.onPrimary else Color.Red
        )

        Text(
            text = "Your profile is" + (if (profile.is_private) "" else " not") + " private!",
            style = MaterialTheme.typography.body2,
            color = if (profile.is_private) MaterialTheme.colors.onPrimary else Color.Red
        )
    }

    Divider(color = Color(0xCCC4C4C4), thickness = 1.dp)

    PropertyWidget(
        key = "Saved playlists",
        value = "You have ${profile.saved_playlists.size} saved playlists",
        isDebug = false
    )

    Row(Modifier.horizontalScroll(scrollState)) {
        for (playlist in profile.saved_playlists)
            PlaylistCard(playlist = playlist)
    }

    Divider(color = Color(0xCCC4C4C4), thickness = 1.dp)

    PropertyWidget(
        key = "Subscribed channels",
        value = "You subscribed to ${profile.subscribed.size} channels",
        isDebug = false
    )
}