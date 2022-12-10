package com.rappi.detail.impl.domain.usecase

import com.rappi.common.domain.model.UIState
import com.rappi.common.domain.model.UIStateResponse
import com.rappi.detail.impl.data.model.toReview
import com.rappi.detail.impl.domain.model.Review
import com.rappi.detail.impl.domain.repository.MovieDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FetchReviewsByIdUC @Inject constructor(private val repository: MovieDetailRepository) {

    fun invoke(id: Int): Flow<UIStateResponse<List<Review>>> {
        return flow {
            try {
                val reviewsResponse = repository.fetchReviewsById(id)
                if (reviewsResponse != null) {
                    emit(
                        UIStateResponse(
                            state = UIState.CONTENT,
                            data = reviewsResponse.results.map { it.toReview() }
                        )
                    )
                } else {
                    emit(UIStateResponse(state = UIState.ERROR))
                }
            } catch (e: Exception) {
                emit(UIStateResponse(state = UIState.ERROR))
            }
        }.flowOn(Dispatchers.IO)
    }
}