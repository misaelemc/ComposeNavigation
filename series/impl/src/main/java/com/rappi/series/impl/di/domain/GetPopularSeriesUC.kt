package com.rappi.series.impl.di.domain

import com.rappi.common.paging.Pageable
import com.rappi.series.impl.di.data.SeriesResponseItem
import com.rappi.series.impl.di.domain.model.Series
import com.rappi.series.impl.di.domain.model.toSeries
import com.rappi.series.impl.di.domain.repositories.SeriesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetPopularSeriesUC  @Inject constructor(private val repository: SeriesRepository) {

    private lateinit var pageable: Pageable<SeriesResponseItem>

    fun getSeries(): Flow<List<Series>> {
        pageable = repository.getMoviesPageable()
        return pageable.data.map {
            when (it) {
                is Pageable.State.Page -> it.data.map { items -> items.toSeries() }
                else -> emptyList()
            }
        }
    }

    suspend fun load() {
        withContext(Dispatchers.IO) { pageable.load() }
    }
}