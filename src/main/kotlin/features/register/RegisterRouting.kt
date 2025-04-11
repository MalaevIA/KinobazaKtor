package com.example.features.register

import com.example.features.cache.InMemoryCache
import com.example.features.cache.TokenCache
import com.example.features.utils.isValidEmail
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Test(val text:String)
fun Application.configureRegisterRouting() {
    routing {
        post("/register") {
            val receive = call.receive<RegisterReceiveRemote>()
            if(!receive.email.isValidEmail()){
                call.respond(HttpStatusCode.BadRequest, "Email is not valid!")
            }

            if(InMemoryCache.userList.map{it.email}.contains(receive.email)){
                call.respond(HttpStatusCode.Conflict,"User already exists")
            }
            val token = UUID.randomUUID().toString()
            InMemoryCache.userList.add(receive)
            InMemoryCache.token.add(TokenCache(email = receive.email, token = token))

            call.respond(RegisterResponseRemote(token = token))
        }
    }
}

