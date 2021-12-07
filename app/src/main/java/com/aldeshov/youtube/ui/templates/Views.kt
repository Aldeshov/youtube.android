package com.aldeshov.youtube.ui.templates

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.aldeshov.youtube.R

@Composable
fun ProfileViewCard(
    title: String,
    subText: String,
    imageURL: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colors.surface)
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier.size(48.dp),
            shape = CircleShape,
            elevation = 2.dp
        ) {
            Image(
                painter = rememberImagePainter(
                    data = imageURL,
                    builder = {
                        crossfade(true)
                        placeholder(R.drawable.ic_placeholder)
                        error(R.drawable.ic_error)
                    }
                ),
                contentDescription = "Avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Column(modifier = Modifier.padding(start = 8.dp)) {
            Text(
                text = title,
                maxLines = 1,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis
            )

            // LocalContentAlpha is defining opacity level of its children
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    maxLines = 1,
                    text = subText,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.body2,
                )
            }
        }
    }
}

@Composable
fun ExpandablePreviewCard(
    expand: Boolean,
    extraPadding: State<Dp> = animateDpAsState(
        if (expand) 6.dp else 16.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessMedium
        )
    ),
    onClick: () -> Unit,
    body: @Composable () -> Unit
) {
    Card(
        shape = AbsoluteRoundedCornerShape(16.dp),
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 2.dp,
        modifier = Modifier
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            )
            .padding(vertical = 8.dp, horizontal = extraPadding.value.coerceAtLeast(0.dp))
    ) {
        Column(modifier = Modifier.clickable(onClick = onClick)) {
            body()
        }
    }
}