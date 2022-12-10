package com.rappi.detail.impl.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rappi.common.domain.model.UIState
import com.rappi.detail.impl.domain.usecase.FetchMovieItemUC
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class MovieDetailViewModel constructor(
    movieId: Int,
    fetchMovieItemUC: FetchMovieItemUC
) : ViewModel() {

    val state = fetchMovieItemUC.fetchMovieById(movieId)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            FetchMovieItemUC.MovieDetailViewState(uiState = UIState.LOADING)
        )
}