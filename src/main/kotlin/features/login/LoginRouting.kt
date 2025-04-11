package com.example.features.login

import com.example.features.cache.InMemoryCache
import com.example.features.cache.TokenCache
import com.example.features.register.RegisterReceiveRemote
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class Test(val text:String)
fun Application.configureLoginRouting() {
    routing {
        post("/login") {
            val receive = call.receive<LoginReceiveRemoteModel>()
            val first = InMemoryCache.userList.first{it.email == receive.email}

            if(first == null){
                call.respond(HttpStatusCode.BadRequest, "User not found")
            }else{
                if (first.password == receive.password){
                    val token = UUID.randomUUID().toString()
                    InMemoryCache.token.add(TokenCache(email = receive.email, token = token))
                    call.respond(LoginResponseRemote(token))
                }else{
                    call.respond(HttpStatusCode.BadRequest, "invalid password")
                }

            }

        }
    }
}