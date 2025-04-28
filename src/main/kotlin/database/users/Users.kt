package com.example.database.users

import org.h2.api.H2Type.row
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object Users : Table("users"){
    val email = varchar("email",25)
    val password = varchar("password",25)
    fun insert(userDTO: UserDTO){
        transaction {
            insert{
                it[email] = userDTO.email
                it[password] = userDTO.password
            }
        }
    }
    fun fetchUser(emailParam: String): UserDTO? {
        return try {
            transaction {
                Users.select { Users.email eq emailParam }
                    .singleOrNull()
                    ?.let { row ->
                        UserDTO(
                            email = row[Users.email],
                            password = row[Users.password]
                        )
                    }
            }
        } catch (e: Exception) {
            null
        }
    }

}