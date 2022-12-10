package com.rappi.common.domain.model

enum class UIState {
    IDLE,
    LOADING,
    ERROR,
    CONTENT
}

data class UIStateResponse<T>(
    val data: T? = null,
    val state: UIState
)