package com.rappi.detail.impl.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rappi.common.domain.model.UIState
import com.rappi.common.domain.model.UIStateResponse
import com.rappi.detail.impl.domain.usecase.FetchReviewsByIdUC
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class ReviewsViewModel constructor(
    movieId: Int,
    fetchReviewsByIdUC: FetchReviewsByIdUC
) : ViewModel() {

    val state = fetchReviewsByIdUC.invoke(movieId)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            UIStateResponse(state = UIState.LOADING)
        )
}