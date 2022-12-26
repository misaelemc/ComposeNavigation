package com.rappi.movie.impl.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.rappi.common.AppScope
import com.rappi.common.IMAGE_URL_PATH
import com.rappi.movie.api.domain.model.Movie
import com.rappi.movie.impl.presentation.presenter.MovieListScreen
import com.slack.circuit.codegen.annotations.CircuitInject

@CircuitInject(MovieListScreen::class, AppScope::class)
@Composable
fun MovieListUi(
    state: MovieListScreen.State,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                contentColor = Color.Black,
                backgroundColor = Color.White,
            ) {
                Text(
                    text = "Most Popular Movies",
                    style = MaterialTheme.typography.h6
                )
            }
        },
    ) { paddingValues ->

        when (state) {
            is MovieListScreen.State.EmptyMovies -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "NO movies"
                )
            }
            MovieListScreen.State.Loading ->
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            is MovieListScreen.State.Success ->
                MovieList(
                    movies = state.movies,
                    paddingValues = paddingValues,
                    eventSink = state.eventSink
                )
        }

    }
}

@Composable
fun MovieList(
    movies: List<Movie>,
    paddingValues: PaddingValues,
    eventSink: (MovieListScreen.MovieEvent) -> Unit,
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = paddingValues.calculateBottomPadding()),
    ) {
        items(movies) { movie ->
            MovieItem(movie = movie!!, onMovieItemClick = {
                eventSink(MovieListScreen.MovieEvent.OpenMovie(movie.id))
            })
            Divider()
        }
    }
}

@Composable
fun MovieItem(movie: Movie, onMovieItemClick: () -> Unit) {
    Surface(
        color = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.onBackground,
        modifier = Modifier.clickable(onClick = onMovieItemClick)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .padding(all = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                MovieImage(
                    movie.backdropUrl.orEmpty(),
                    modifier = Modifier.size(120.dp)
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp)
                ) {
                    Text(
                        text = movie.title,
                        maxLines = 2,
                        style = MaterialTheme.typography.h6,
                        overflow = TextOverflow.Ellipsis
                    )
                    if (movie.overview != null) {
                        Text(
                            text = movie.overview!!,
                            maxLines = 3,
                            style = MaterialTheme.typography.body1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MovieImage(
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
        modifier = modifier,
        contentDescription = "Movie Item Image",
        contentScale = ContentScale.Crop
    )
}