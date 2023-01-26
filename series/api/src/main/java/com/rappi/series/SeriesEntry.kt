package com.rappi.series

import com.rappi.navigation.ScreenRoutes
import com.rappi.navigation.SingleFeatureEntry

abstract class SeriesEntry : SingleFeatureEntry {

    override val featureRoute: String = ScreenRoutes.SERIES.value
}