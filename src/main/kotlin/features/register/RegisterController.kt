package com.example.features.register

import com.example.database.tokens.TokenDTO
import com.example.database.tokens.Tokens
import com.example.database.users.UserDTO
import com.example.database.users.Users
import com.example.features.utils.isValidEmail
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import org.jetbrains.exposed.exceptions.ExposedSQLException
import java.util.UUID

class RegisterController(val call: ApplicationCall){
    suspend fun registerNewUser(){
        val registerReceiveRemote = call.receive<RegisterReceiveRemote>()
        if (!registerReceiveRemote.email.isValidEmail()){
            call.respond(HttpStatusCode.BadRequest,"Email is not valid")
        }
        val userDTO = Users.fetchUser(registerReceiveRemote.email)
        if (userDTO != null){
            call.respond(HttpStatusCode.Conflict, "User already exists")
        } else {
            val token = UUID.randomUUID().toString()
            try {
                Users.insert(
                    UserDTO(
                        email = registerReceiveRemote.email,
                        password = registerReceiveRemote.password
                    )
                )
            }
            catch (e: ExposedSQLException){
                call.respond(HttpStatusCode.Conflict, "User already exists")
            }
            Tokens.insert(
                TokenDTO(
                    rowId = UUID.randomUUID().toString(),
                    email = registerReceiveRemote.email,
                    token = token
                )
            )
            call.respond(RegisterResponseRemote(token =  token))
        }
    }
}