package com.rappi.detail.impl.presentation.screen

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.rappi.common.AppScope
import com.rappi.common.IMAGE_URL_PATH
import com.rappi.common.LARGE_IMAGE_URL_PATH
import com.rappi.common.presentation.widget.ErrorItem
import com.rappi.common.presentation.widget.LoadingView
import com.rappi.detail.api.MovieDetail
import com.rappi.detail.api.MovieDetailScreen
import com.rappi.movie.api.domain.model.Movie
import com.slack.circuit.codegen.annotations.CircuitInject

@CircuitInject(MovieDetailScreen::class, AppScope::class)
@Composable
fun MovieDetailUi(
    movieDetailState: MovieDetailScreen.State
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (movieDetailState is MovieDetailScreen.State.Success) movieDetailState.movieDetail.movie.title else "",
                        style = MaterialTheme.typography.h6
                    )
                },
                contentColor = Color.Black,
                backgroundColor = Color.White,
                navigationIcon = {
                    IconButton(onClick = { movieDetailState.eventSink(MovieDetailScreen.MovieEvent.OnBackPressed) }) {
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = {
                        movieDetailState.eventSink(
                            MovieDetailScreen.MovieEvent.OpenReview(
                                1
                            )
                        )
                    }) {
                        Icon(imageVector = Icons.Outlined.Info, contentDescription = null)
                    }
                }
            )
        },
        modifier = Modifier.statusBarsPadding(),
    ) {
        val modifier = Modifier.padding(it)
        Crossfade(movieDetailState) { uiState ->
            when (uiState) {
                is MovieDetailScreen.State.Loading -> LoadingView(modifier = modifier.fillMaxSize())
                is MovieDetailScreen.State.Success -> MovieDetailContent(
                    uiState.movieDetail,
                    modifier = modifier
                ) { recommendedId ->
                    uiState.successEventSink.invoke(
                        MovieDetailScreen.State.Success.SuccessMovieEvent.OpenRecommended(
                            recommendedId
                        )
                    )
                }
                is MovieDetailScreen.State.EmptyMovie -> {
                    ErrorItem(
                        message = "Error occurred",
                        onClickRetry = { }
                    )
                }
            }
        }
    }
}

@Composable
fun MovieDetailContent(
    detail: MovieDetail,
    modifier: Modifier = Modifier,
    onRecommendedClicked: (Int) -> Unit
) {
    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        MovieDetailImage(
            imageUrl = "$LARGE_IMAGE_URL_PATH/${detail.movie.backdropUrl.orEmpty()}",
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
        )
        detail.movie.overview?.let {
            Text(
                modifier = Modifier.padding(all = 16.dp),
                text = it,
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.body1,
            )
        }
        if (detail.recommended.isNotEmpty()) {
            Text(
                modifier = Modifier.padding(all = 16.dp),
                text = "Recommendations",
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.h6,
            )
            LazyRow(
                modifier = Modifier.padding(bottom = 16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(detail.recommended) {
                    MovieCard(it, onRecommendedClicked)
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
        }
    }
}

@Composable
fun MovieDetailImage(
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build()
    )

    Image(
        painter = painter,
        modifier = modifier,
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
}

@Composable
fun MovieCard(movie: Movie, onClick: (Int) -> Unit) {
    Column(modifier = Modifier
        .width(140.dp)
        .clickable { onClick.invoke(movie.id) }) {
        MovieDetailImage(
            imageUrl = "$IMAGE_URL_PATH/${movie.backdropUrl.orEmpty()}",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = movie.title,
            maxLines = 2,
            style = MaterialTheme.typography.subtitle1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start
        )
    }
}