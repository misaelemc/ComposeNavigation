package com.rappi.series.impl.di.data.respositories

import com.rappi.common.paging.Pageable
import com.rappi.series.impl.di.SeriesFeatureScope
import com.rappi.series.impl.di.data.SeriesResponseItem
import com.rappi.series.impl.di.data.SeriesService
import com.rappi.series.impl.di.domain.repositories.SeriesRepository
import com.squareup.anvil.annotations.ContributesBinding
import javax.inject.Inject


@ContributesBinding(scope = SeriesFeatureScope::class, boundType = SeriesRepository::class)
class SeriesRepositoryImpl @Inject constructor(private val api: SeriesService) : SeriesRepository {

    override fun getMoviesPageable(): Pageable<SeriesResponseItem> {
        return object : Pageable<SeriesResponseItem>() {

            override suspend fun load() {
                if (hasMorePages) {
                    val response = api.fetchSeries(page)
                    if (response.results.isEmpty()) {
                        emit(State.Page(emptyList(), page))
                    } else {
                        val nextPage = if (page < response.totalPages) page++ else null
                        emit(State.Page(response.results, page, nextPage))
                    }
                } else {
                    emit(State.Page(emptyList(), page))
                }
            }
        }
    }
}