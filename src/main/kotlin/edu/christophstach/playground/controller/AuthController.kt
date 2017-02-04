/*
 * Copyright (c) 2017 Christoph Stach <christoph.stach@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package edu.christophstach.playground.controller

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import edu.christophstach.playground.data.response.LoginResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*


/**
 * @author Christoph Stach - s0555912@htw-berlin.de
 * @since 1/17/17
 */
@RestController
@RequestMapping("/auth")
class AuthController {
    @Value("\${jwt.secret}")
    private val secret = ""

    @Value("\${jwt.issuer}")
    private val issuer = ""

    @Value("\${jwt.expiration}")
    private val expiration = 0L


    class TokenRepresentation(val token: JWT) {
        override fun toString(): String {
            var s = "\nTokenRepresentation("

            token.claims.entries.forEach {
                when (it.key) {
                    "exp" -> s += "\n\t" + it.key + ":" + it.value.asDate()
                    "iss", "name", "sub",
                    "given_name", "family_name",
                    "gender", "email", "role" -> s += "\n\t" + it.key + ":" + it.value.asString()
                    else -> s += "\n\t" + it.key + ":" + it.value.asBoolean()
                }
            }

            s += "\n)"

            return s
        }
    }

    @GetMapping("/login")
    fun login(): LoginResponse {
        val expires: Date = Date.from(
                LocalDateTime
                        .from(LocalDateTime.now())
                        .plusSeconds(expiration)
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
        )

        val token = JWT.create()
                .withIssuer(issuer)
                .withExpiresAt(expires)
                .withClaim("ALL", false)
                .withSubject("christoph.stach@gmail.com")

                //BEGIN: IANA
                .withClaim("name", "Christoph Stach")
                .withClaim("given_name", "Christoph")
                .withClaim("family_name", "Stach")
                .withClaim("gender", "MALE")
                .withClaim("email", "christoph.stach@gmail.com")
                .withClaim("email_verified", true)
                //END: IANA
                .withClaim("role", "ADMIN")
                .sign(Algorithm.HMAC256(secret))

        return LoginResponse(true, token, expires)
    }

    @GetMapping("/logout")
    fun logout(): String {
        return "Logout"
    }

    @GetMapping("/status")
    fun status(): String {
        return "Status"
    }
}