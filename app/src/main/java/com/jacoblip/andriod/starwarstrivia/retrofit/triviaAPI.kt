package com.jacoblip.andriod.starwarstrivia.retrofit

import com.jacoblip.andriod.starwarstrivia.response.Character
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query

interface triviaAPI {

    @GET("/api/people/{number}/")
    suspend fun getCharacter(
       @Path("number")number:Int
    ):Response<Character>
}