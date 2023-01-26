package com.rappi.series.impl.di.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movies.viewmodel.annotations.ContributesViewModel
import com.rappi.series.impl.di.SeriesFeatureScope
import com.rappi.series.impl.di.domain.GetPopularSeriesUC
import com.rappi.series.impl.di.domain.model.Series
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@ContributesViewModel(SeriesFeatureScope::class)
class SeriesScreenViewModel @AssistedInject constructor(
    @Assisted handle: SavedStateHandle,
    private val getPopularSeriesUC: GetPopularSeriesUC
) : ViewModel() {


    val state = getPopularSeriesUC.getSeries()
        .map {
            if (it.isEmpty()) {
                State.Loading
            } else {
                State.Success(it)
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            State.Loading
        )

    fun loadMore() {
        viewModelScope.launch {
            getPopularSeriesUC.load()
        }
    }

    sealed class State {
        object Empty : State()
        object Loading : State()
        data class Success(val data: List<Series>) : State()
    }
}