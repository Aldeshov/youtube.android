package com.aldeshov.youtube.ui.activity.main.contents.views

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.aldeshov.youtube.service.models.api.content.VideoContent

@Composable
fun FavouriteBadge() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(5.dp),
        elevation = 2.dp,
        contentColor = Color.White,
        backgroundColor = MaterialTheme.colors.primary,
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "Favourite Badge Icon",
                modifier = Modifier.padding(end = 8.dp)
            )

            Text(
                text = "Favourites",
                fontWeight = FontWeight.ExtraBold,
                style = MaterialTheme.typography.h5
            )
        }
    }
}

@Composable
fun ContentList(
    list: List<VideoContent>,
    currentItem: Int,
    setCurrentItem: (index: Int) -> Unit,
    watchAction: (code: String) -> Unit,
    isFavourite: Boolean
) {
    val scrollState = rememberLazyListState()

    LazyColumn(state = scrollState) {
        if (isFavourite) {
            item {
                FavouriteBadge()
            }
        }

        items(list.size) {
            Content(
                videoContent = list[it],
                isSelected = currentItem == it,
                onClick = { if (currentItem == it) setCurrentItem(-1) else setCurrentItem(it) },
                watchAction = watchAction,
                isFavourite = isFavourite
            )
        }
    }
}