package com.example.features.favorite

import com.example.database.Favorite.Favorites
import com.example.database.tokens.Tokens
import io.ktor.http.HttpStatusCode
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import io.ktor.server.application.*
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureFavoritesRouting() {
    routing {
        route("/favorites") {
            post("/add") {
                val token = call.request.headers["Authorization"]?.removePrefix("Bearer ")
                val request = call.receive<FavoriteRequest>()

                val email = Tokens.getEmailByToken(token)
                if (email != null) {
                    Favorites.addToFavorites(email, request.movieId)
                    call.respond(HttpStatusCode.OK)
                } else {
                    call.respond(HttpStatusCode.Unauthorized)
                }
            }

            get {
                val token = call.request.headers["Authorization"]?.removePrefix("Bearer ")
                val email = Tokens.getEmailByToken(token)
                if (email != null) {
                    val favoriteList = Favorites.getFavorites(email)
                    call.respond(FavoriteResponse(favorites = favoriteList))
                } else {
                    call.respond(HttpStatusCode.Unauthorized)
                }
            }

            delete("/remove") {
                val token = call.request.headers["Authorization"]?.removePrefix("Bearer ")
                val request = call.receive<FavoriteRequest>()

                val email = Tokens.getEmailByToken(token)
                if (email != null) {
                    Favorites.removeFromFavorites(email, request.movieId)
                    call.respond(HttpStatusCode.OK)
                } else {
                    call.respond(HttpStatusCode.Unauthorized)
                }
            }
        }
    }
}
fun Tokens.getEmailByToken(token: String?): String? {
    if (token == null) return null
    return transaction {
        Tokens.select { Tokens.token eq token }
            .singleOrNull()?.get(Tokens.email)
    }
}
