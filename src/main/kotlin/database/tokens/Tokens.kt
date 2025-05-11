package com.example.database.tokens

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

object Tokens : Table("tokens") {
    val email = Tokens.varchar("email",25)
    val id = Tokens.varchar("id",50)
    val token = Tokens.varchar("token",50)

    fun insert(tokenDTO: TokenDTO){
        transaction {
            Tokens.insert{
                it[email] = tokenDTO.email
                it[id] = tokenDTO.rowId
                it[token] = tokenDTO.token
            }
        }
    }
}