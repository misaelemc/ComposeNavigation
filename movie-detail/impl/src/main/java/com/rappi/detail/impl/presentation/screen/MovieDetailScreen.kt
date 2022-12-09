package com.rappi.detail.impl.presentation.screen

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.rappi.common.LARGE_IMAGE_URL_PATH
import com.rappi.common.domain.model.UIState
import com.rappi.common.presentation.widget.ErrorItem
import com.rappi.common.presentation.widget.LoadingView
import com.rappi.detail.impl.presentation.viewModel.MovieDetailViewModel
import com.rappi.movie.api.domain.model.Movie

@Composable
fun MovieDetailScreen(
    movieId: Int,
    viewModel: MovieDetailViewModel,
    onBackPressed: () -> Unit,
) {
    val viewState by viewModel.viewStateFlow.collectAsState()
    viewModel.fetchMovie(movieId)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = viewState.movie?.title ?: "", style = MaterialTheme.typography.h6)
                },
                contentColor = Color.Black,
                backgroundColor = Color.White,
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        modifier = Modifier.statusBarsPadding(),
    ) {
        val modifier = Modifier.padding(it)
        Crossfade(viewState.uiState) { uiState ->
            when (uiState) {
                UIState.LOADING -> LoadingView(modifier = modifier.fillMaxSize())
                UIState.CONTENT -> MovieDetailContent(viewState.movie!!, modifier = modifier)
                UIState.ERROR -> {
                    ErrorItem(
                        message = "Error occurred",
                        onClickRetry = { viewModel.fetchMovie(movieId) }
                    )
                }
                UIState.IDLE -> {
                    // do nothing
                }
            }
        }
    }
}

@Composable
fun MovieDetailContent(movie: Movie, modifier: Modifier = Modifier) {
    Column(modifier.fillMaxSize()) {
        MovieDetailImage(
            imageUrl = movie.backdropUrl.orEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
        )

        Column(Modifier.verticalScroll(rememberScrollState())) {
            MovieDetailDescription(description = movie.overview!!)
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
            .data("$LARGE_IMAGE_URL_PATH/$imageUrl")
            .crossfade(true)
            .build()
    )

    Image(
        painter = painter,
        modifier = modifier,
        contentDescription = "Movie Detail Poster",
        contentScale = ContentScale.Crop
    )
}

@Composable
fun MovieDetailDescription(description: String) {
    Text(
        modifier = Modifier.padding(all = 16.dp),
        text = description,
        letterSpacing = 2.sp,
        color = MaterialTheme.colors.onBackground
    )
}