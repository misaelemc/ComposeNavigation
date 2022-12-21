package com.rappi.detail.impl.domain.usecase

import com.rappi.detail.impl.data.model.toReview
import com.rappi.detail.impl.domain.model.Review
import com.rappi.detail.impl.domain.repository.MovieDetailRepository
import javax.inject.Inject

class FetchReviewsByIdUC @Inject constructor(private val repository: MovieDetailRepository) {

    suspend fun invoke(id: Int): List<Review>? {
        return try {
            val reviewsResponse = repository.fetchReviewsById(id)
            reviewsResponse?.results?.map { it.toReview() }
        } catch (e: Exception) {
            null
        }
    }
}