package com.example.features.login

import com.example.database.tokens.TokenDTO
import com.example.database.tokens.Tokens
import com.example.database.users.Users
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import java.util.UUID

class LoginController(private val call: ApplicationCall) {
    suspend fun performLogin(){
        val receive = call.receive<LoginReceiveRemoteModel>()
        val userDTO = Users.fetchUser(receive.email)

        if(userDTO == null){
            call.respond(HttpStatusCode.BadRequest,"User not Found")
        }
        else{
            if (userDTO.password == receive.password){
                val token = UUID.randomUUID().toString()
                Tokens.insert(
                    TokenDTO(
                        rowId = UUID.randomUUID().toString(),
                        email = receive.email,
                        token = token
                    )
                )
                call.respond(LoginResponseRemote(token = token))
            }
            else{
                call.respond(HttpStatusCode.BadRequest,"Invalid password")
            }
        }
    }
}