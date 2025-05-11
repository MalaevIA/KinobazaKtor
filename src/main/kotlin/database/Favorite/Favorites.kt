package com.example.database.Favorite

import com.example.database.users.Users
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object Favorites : Table("favorites") {
    val userEmail = varchar("user_email", 50).references(Users.email)
    val movieId = varchar("movie_id", 50)

    override val primaryKey = PrimaryKey(userEmail, movieId)

    fun addToFavorites(email: String, movieId: String) {
        transaction {
            Favorites.insertIgnore {
                it[userEmail] = email
                it[Favorites.movieId] = movieId
            }
        }
    }

    fun getFavorites(email: String): List<String> {
        return transaction {
            Favorites.select { Favorites.userEmail eq email }
                .map { it[Favorites.movieId] }
        }
    }

    fun removeFromFavorites(email: String, movieId: String) {
        transaction {
            Favorites.deleteWhere {
                (Favorites.userEmail eq email)  and (Favorites.movieId eq movieId)
            }
        }
    }
}