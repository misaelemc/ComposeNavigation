package com.rappi.detail.impl.presentation.screen

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.rappi.common.AppScope
import com.rappi.common.IMAGE_URL_PATH
import com.rappi.common.presentation.widget.ErrorItem
import com.rappi.common.presentation.widget.LoadingView
import com.rappi.detail.impl.domain.model.Review
import com.rappi.detail.impl.presentation.presenter.ReviewsScreen
import com.slack.circuit.codegen.annotations.CircuitInject

@CircuitInject(ReviewsScreen::class, AppScope::class)
@Composable
fun ReviewsUi(
    state: ReviewsScreen.ReviewsState,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Reviews",
                        style = MaterialTheme.typography.h6
                    )
                },
                contentColor = Color.Black,
                backgroundColor = Color.White,
                navigationIcon = {
                    IconButton(onClick = { state.eventSink.invoke(ReviewsScreen.Event.OnBackPressed) }) {
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
    ) { paddingValues ->
        ReviewContent(
            state = state,
            paddingValues = paddingValues,
        )
    }
}

@Composable
fun ReviewContent(state: ReviewsScreen.ReviewsState, paddingValues: PaddingValues) {

    Crossfade(state) { uiState ->
        when (uiState) {
            is ReviewsScreen.ReviewsState.Loading -> LoadingView(modifier = Modifier.fillMaxSize())
            is ReviewsScreen.ReviewsState.Success -> ReviewsList(uiState.reviews, paddingValues)
            is ReviewsScreen.ReviewsState.NoReviews -> {
                ErrorItem(
                    message = "Error occurred",
                    onClickRetry = { }
                )
            }

        }
    }
}

@Composable
fun ReviewsList(items: List<Review>, paddingValues: PaddingValues) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = paddingValues.calculateBottomPadding()),
    ) {
        items(items) { review ->
            ReviewItem(review = review)
            Divider()
        }
    }
}

@Composable
fun ReviewItem(review: Review) {
    Surface(
        color = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.onBackground,
    ) {
        Column {
            Row(
                modifier = Modifier
                    .padding(all = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ReviewImage(
                    imageUrl = review.avatar.orEmpty(),
                    modifier = Modifier.size(120.dp)
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp)
                ) {
                    Text(
                        text = review.name,
                        maxLines = 2,
                        style = MaterialTheme.typography.h6,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = review.content,
                        maxLines = 3,
                        style = MaterialTheme.typography.body1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
fun ReviewImage(
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current)
            .data("$IMAGE_URL_PATH/$imageUrl")
            .crossfade(true)
            .build()
    )

    Image(
        painter = painter,
        modifier = modifier.clip(CircleShape),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
}