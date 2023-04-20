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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.compose.items
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rappi.common.IMAGE_URL_PATH
import com.rappi.common.presentation.widget.ErrorItem
import com.rappi.common.presentation.widget.LoadingItem
import com.rappi.common.presentation.widget.LoadingView
import com.rappi.common.viewModel.compose.daggerViewModel
import com.rappi.movie.api.domain.model.Movie
import com.rappi.movie.impl.presentation.viewModel.MovieViewModel

@Composable
@Destination
@RootNavGraph(start = true)
fun MovieScreen(
    onMovieItemClick: (Int) -> Unit,
    viewModel: MovieViewModel = daggerViewModel(),
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
            viewModel = viewModel,
            paddingValues = paddingValues,
            onMovieItemClick = onMovieItemClick
        )
    }
}

@Composable
fun MovieList(
    viewModel: MovieViewModel,
    paddingValues: PaddingValues,
    onMovieItemClick: (Int) -> Unit,
    lazyListState: LazyListState = rememberLazyListState(),
) {
    val movieState = viewModel.state.collectAsState(initial = MovieViewModel.State.Loading)
    val isLoading = movieState.value is MovieViewModel.State.Loading
    ScrollEndEffect(
        onLoadMore = { viewModel.loadMore() },
        scrollState = lazyListState,
        isLoading = isLoading
    )
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = paddingValues.calculateBottomPadding()),
        state = lazyListState
    ) {
        when (val stateValue = movieState.value) {
            MovieViewModel.State.Empty -> {
                item {
                    ErrorItem(
                        message = "Empty",
                        onClickRetry = { }
                    )
                }
            }
            MovieViewModel.State.Loading -> {
                item { LoadingView(modifier = Modifier.fillParentMaxSize()) }
            }
            is MovieViewModel.State.Success -> {
                items(stateValue.data) { movie ->
                    MovieItem(movie = movie, onMovieItemClick = {
                        onMovieItemClick(movie.id)
                    })
                    Divider()
                }
            }
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

@Composable
fun ScrollEndEffect(
    onLoadMore: () -> Unit,
    scrollState: LazyListState,
    isLoading: Boolean = false
) {
    val endReached by remember { derivedStateOf { scrollState.isScrolledToTheEnd() } }
    LaunchedEffect(key1 = endReached) {
        if (endReached && !isLoading) {
            onLoadMore.invoke()
        }
    }
}

fun LazyListState.isScrolledToTheEnd() =
    layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1
