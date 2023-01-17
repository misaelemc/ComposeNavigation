package com.rappi.movie.impl.presentation.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rappi.common.viewModel.ViewModelAssistedFactory
import com.rappi.movie.api.domain.model.Movie
import com.rappi.movie.impl.domain.usecase.FetchMoviesUC
import com.rappi.movie.impl.presentation.paging.MoviePagingSource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow

class MovieViewModel @AssistedInject constructor(
    @Assisted private val handle: SavedStateHandle,
    private val fetchMoviesUC: FetchMoviesUC
) : ViewModel() {

    val movies: Flow<PagingData<Movie>> = Pager(PagingConfig(pageSize = PAGE_SIZE)) {
        MoviePagingSource(fetchMoviesUC)
    }.flow.cachedIn(viewModelScope)

    companion object {
        const val PAGE_SIZE = 20
    }

    @AssistedFactory
    interface Factory : ViewModelAssistedFactory<MovieViewModel>
}