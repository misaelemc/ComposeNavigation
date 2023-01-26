package com.rappi.series.impl.di.domain.repositories

import com.rappi.common.paging.Pageable
import com.rappi.series.impl.di.data.SeriesResponseItem


interface SeriesRepository {

    fun getMoviesPageable(): Pageable<SeriesResponseItem>

}