package com.example.features.login

import ch.qos.logback.core.subst.Token
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer

@Serializable
data class LoginReceiveRemoteModel(
    val email:String,
    val password: String
)

@Serializable
data class LoginResponseRemote(
    val token: String
)

