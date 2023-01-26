package com.rappi.series.impl.di.domain.model

import com.rappi.series.impl.di.data.SeriesResponseItem

class Series(val id: Int, val title: String, val backdropUrl: String?, val overview: String) {
}

fun SeriesResponseItem.toSeries(): Series {
    return Series(this.id, this.name, this.backdropUrl, this.overview.orEmpty())
}