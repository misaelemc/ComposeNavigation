package com.rappi.movie.impl.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.rappi.common.IMAGE_URL_PATH
import com.rappi.common.presentation.widget.ErrorItem
import com.rappi.common.presentation.widget.LoadingItem
import com.rappi.common.presentation.widget.LoadingView
import com.rappi.movie.api.domain.model.Movie
import com.rappi.movie.impl.presentation.viewModel.MovieViewModel
import kotlinx.coroutines.flow.Flow

@Composable
fun MovieScreen(
    viewModel: MovieViewModel,
    onMovieItemClick: (Int) -> Unit,
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
        MovieList(
            movies = viewModel.movies,
            paddingValues = paddingValues,
            onMovieItemClick = onMovieItemClick
        )
    }
}

@Composable
fun MovieList(
    movies: Flow<PagingData<Movie>>,
    paddingValues: PaddingValues,
    onMovieItemClick: (Int) -> Unit,
) {
    val lazyMovieItems = movies.collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = paddingValues.calculateBottomPadding())
    ) {
        items(lazyMovieItems) { movie ->
            MovieItem(movie = movie!!, onMovieItemClick = onMovieItemClick)
            Divider()
        }

        lazyMovieItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { LoadingView(modifier = Modifier.fillParentMaxSize()) }
                }
                loadState.append is LoadState.Loading -> {
                    item { LoadingItem() }
                }
                loadState.refresh is LoadState.Error -> {
                    val e = lazyMovieItems.loadState.refresh as LoadState.Error
                    item {
                        ErrorItem(
                            message = e.error.localizedMessage!!,
                            modifier = Modifier.fillParentMaxSize(),
                            onClickRetry = { retry() }
                        )
                    }
                }
                loadState.append is LoadState.Error -> {
                    val e = lazyMovieItems.loadState.append as LoadState.Error
                    item {
                        ErrorItem(
                            message = e.error.localizedMessage!!,
                            onClickRetry = { retry() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MovieItem(movie: Movie, onMovieItemClick: (Int) -> Unit) {
    Surface(
        color = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.onBackground,
        modifier = Modifier.clickable(onClick = { onMovieItemClick(movie.id) })
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