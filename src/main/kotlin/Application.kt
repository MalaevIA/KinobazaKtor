package com.example

import com.example.features.favorite.configureFavoritesRouting
import com.example.features.login.configureLoginRouting
import com.example.features.register.configureRegisterRouting
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import org.jetbrains.exposed.sql.Database

fun main() {

    Database.connect("jdbc:postgresql://localhost:5432/kinobaza",
        "org.postgresql.Driver",
        "postgres",
        "1111")
    embeddedServer(CIO, port = 8080, host = "0.0.0.0") {
        configureRegisterRouting()
        configureLoginRouting()
        configureFavoritesRouting()
        configureSerialization()
        configureRouting()
    }.start(wait = true)

}