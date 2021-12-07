package com.aldeshov.youtube.ui.templates

import android.text.TextUtils
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BugReport
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aldeshov.youtube.R

@Composable
fun BrandHeaderWidget(
    subText: String = stringResource(id = R.string.brand_sub_text)
) {
    Column(
        modifier = Modifier
            .padding(14.dp)
            .fillMaxWidth()
    ) {
        Text(
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onSurface,
            fontWeight = FontWeight(600),
            style = MaterialTheme.typography.h3,
            text = stringResource(R.string.app_name),
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        )

        if (!TextUtils.isEmpty(subText)) {
            Text(
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.primary,
                text = subText
            )
        }
    }
}

@Composable
fun TermsTextWidget() {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .padding(bottom = 32.dp)
    ) {
        Text(
            fontSize = 12.sp,
            color = Color.Black,
            style = MaterialTheme.typography.body2,
            text = stringResource(id = R.string.terms_sub_text)
        )

        Text(
            fontSize = 12.sp,
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.body2,
            text = stringResource(id = R.string.terms_sub_text_link),
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}

@Composable
fun ErrorWidget(message: String) {
    Card(
        elevation = 2.dp,
        shape = AbsoluteRoundedCornerShape(5.dp),
        backgroundColor = Color(0xFFFFC3C3),
        modifier = Modifier.padding(12.dp),
        contentColor = Color.Red
    ) {
        Text(
            text = message,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
fun WarningWidget(text: String = stringResource(id = R.string.connection_error)) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(R.drawable.ic_danger),
            contentDescription = "Danger Icon",
            modifier = Modifier.size(128.dp)
        )

        Text(
            text = text,
            color = Color.DarkGray
        )
    }
}

@Composable
fun LoadingWidget() {
    CircularProgressIndicator(
        color = MaterialTheme.colors.onPrimary,
        modifier = Modifier.padding(24.dp),
        strokeWidth = 3.dp
    )
}

@Composable
fun EmptyWidget(text: String) {
    Text(
        text = text,
        modifier = Modifier.padding(24.dp),
        color = MaterialTheme.colors.onPrimary,
        style = MaterialTheme.typography.body1,
    )
}

@Composable
fun PropertyWidget(key: String, value: String, isDebug: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Icon(
            imageVector = Icons.Outlined.BugReport,
            contentDescription = "Debug Icon",
            modifier = Modifier.padding(start = 8.dp),
            tint = if (isDebug) MaterialTheme.colors.onPrimary else Color.Transparent
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp)
        ) {
            Text(
                text = key,
                maxLines = 1,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // LocalContentAlpha is defining opacity level of its children
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.subtitle2,
                )
            }
        }
    }
}