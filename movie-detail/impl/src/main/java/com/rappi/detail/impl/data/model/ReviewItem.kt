package com.rappi.detail.impl.data.model

import com.google.gson.annotations.SerializedName
import com.rappi.detail.impl.domain.model.Review

data class ReviewsResponse(val results: List<ReviewItem>)

data class ReviewItem(
    @SerializedName("author") val author: String,
    @SerializedName("content") val content: String,
    @SerializedName("author_details") val detail: AuthorDetail,
)

data class AuthorDetail(
    @SerializedName("name") val name: String,
    @SerializedName("username") val username: String,
    @SerializedName("avatar_path") val avatar: String?,
)

fun ReviewItem.toReview(): Review {
    return Review(
        name = detail.name,
        author = author,
        avatar = detail.avatar,
        content = content,
        username = detail.username
    )
}