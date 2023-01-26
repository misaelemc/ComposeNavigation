package com.rappi.series.impl.di.presentation

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
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.rappi.common.IMAGE_URL_PATH
import com.rappi.common.presentation.widget.ErrorItem
import com.rappi.common.presentation.widget.LoadingView
import com.rappi.series.impl.di.domain.model.Series

@Composable
fun SeriesScreen(viewModel: SeriesScreenViewModel) {

    val seriesState by viewModel.state.collectAsState(initial = SeriesScreenViewModel.State.Loading)

    Scaffold(
        topBar = {
            TopAppBar(
                contentColor = Color.Black,
                backgroundColor = Color.White,
            ) {
                Text(
                    text = "Most Popular Series",
                    style = MaterialTheme.typography.h6
                )
            }
        },
    ) { paddingValues ->
        SeriesList(
            state = seriesState,
            paddingValues = paddingValues,
            onSeriesItemClick = { },
            onLoadMoreClick = { viewModel.loadMore() }
        )
    }

}


@Composable
fun SeriesList(
    state: SeriesScreenViewModel.State,
    paddingValues: PaddingValues,
    onSeriesItemClick: (Int) -> Unit,
    onLoadMoreClick: () -> Unit,
    lazyListState: LazyListState = rememberLazyListState(),
) {
    val isLoading = state is SeriesScreenViewModel.State.Loading
    ScrollEndEffect(
        onLoadMore = { onLoadMoreClick.invoke() },
        scrollState = lazyListState,
        isLoading = isLoading
    )
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = paddingValues.calculateBottomPadding()),
        state = lazyListState
    ) {
        when (state) {
            SeriesScreenViewModel.State.Empty -> {
                item {
                    ErrorItem(
                        message = "Empty",
                        onClickRetry = { }
                    )
                }
            }
            SeriesScreenViewModel.State.Loading -> {
                item { LoadingView(modifier = Modifier.fillParentMaxSize()) }
            }
            is SeriesScreenViewModel.State.Success -> {

                items(state.data) { series: Series ->
                    SeriesItem(series = series, onMovieItemClick = {
                        onSeriesItemClick(series.id)
                    })
                    Divider()
                }
            }
        }
    }
}

@Composable
fun SeriesItem(series: Series, onMovieItemClick: () -> Unit) {
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
                    series.backdropUrl.orEmpty(),
                    modifier = Modifier.size(120.dp)
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp)
                ) {
                    Text(
                        text = series.title,
                        maxLines = 2,
                        style = MaterialTheme.typography.h6,
                        overflow = TextOverflow.Ellipsis
                    )
                    if (series.overview.isNotBlank()) {
                        Text(
                            text = series.overview,
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

