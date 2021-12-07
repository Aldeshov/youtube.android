package com.aldeshov.youtube.ui.activity.main.account.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.aldeshov.youtube.BASE_URL
import com.aldeshov.youtube.R
import com.aldeshov.youtube.service.database.models.LocalUser
import com.aldeshov.youtube.service.models.LiveStatus
import com.aldeshov.youtube.ui.activity.main.account.AccountViewModel
import com.aldeshov.youtube.ui.activity.main.account.ProfileViewModel
import com.aldeshov.youtube.ui.templates.DefaultViewModelScreen
import com.aldeshov.youtube.ui.templates.ProfileViewCard
import com.aldeshov.youtube.ui.templates.PropertyWidget

@Composable
fun UserInfo(localUser: LocalUser, logoutAction: () -> Unit) {
    Column {
        Row(
            modifier = Modifier.padding(32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = Modifier.size(128.dp),
                shape = CircleShape,
                elevation = 2.dp
            ) {
                Image(
                    painter = rememberImagePainter(
                        data = "$BASE_URL${localUser.avatar}",
                        builder = {
                            crossfade(true)
                            placeholder(R.drawable.ic_placeholder)
                            error(R.drawable.ic_error)
                        }
                    ),
                    contentDescription = "User Avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Column {
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = "Hello, ${localUser.full_name!!}",
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold
                )

                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                    content = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_logout),
                                contentDescription = "Logout"
                            )
                            Spacer(modifier = Modifier.padding(4.dp))
                            Text("Log out", color = Color.White)
                        }
                    },
                    onClick = logoutAction,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }

        localUser.email?.let { PropertyWidget("Email", it, false) }

        Divider(color = Color(0xCCC4C4C4), thickness = 1.dp)

        localUser.token?.let { PropertyWidget("JWT Token", it, true) }
    }
}

@Composable
fun Account(
    accountViewModel: AccountViewModel,
    profileViewModel: ProfileViewModel,
    logoutAction: () -> Unit
) {
    val scrollState = rememberLazyListState()

    LazyColumn(state = scrollState) {
        item {
            UserInfo(accountViewModel.user, logoutAction)
        }

        item {
            DefaultViewModelScreen(
                viewModel = profileViewModel,
                toLoad = { profileViewModel.getMyProfile() },
                emptyText = "There is no profile",
                showWarningIcon = false
            ) {
                Profile(profileViewModel.profile)
            }
        }

        if (profileViewModel.status == LiveStatus.LOADED_SUCCESSFUL) {
            items(items = profileViewModel.profile.subscribed) {
                Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp)) {
                    ProfileViewCard(
                        title = it.name,
                        subText = "${it.subscribers} subscribers",
                        imageURL = "$BASE_URL${it.avatar}"
                    ) {}
                }
            }
        }
    }
}