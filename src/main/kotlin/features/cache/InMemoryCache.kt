package com.example.features.cache

import com.example.features.register.RegisterReceiveRemote

data class TokenCache(
    val email: String,
    val token: String
)

object InMemoryCache {
    val userList: MutableList<RegisterReceiveRemote> = mutableListOf()
    val token: MutableList<TokenCache> = mutableListOf()
}