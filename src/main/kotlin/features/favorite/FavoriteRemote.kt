package com.example.features.favorite

import kotlinx.serialization.Serializable


@Serializable
data class FavoriteRequest(
    val movieId: String
)

@Serializable
data class FavoriteResponse(
    val favorites: List<String>
)