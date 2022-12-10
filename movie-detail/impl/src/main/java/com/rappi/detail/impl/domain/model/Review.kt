package com.rappi.detail.impl.domain.model

data class Review(
    val name: String,
    val author: String,
    val avatar: String?,
    val content: String,
    val username: String,
)
