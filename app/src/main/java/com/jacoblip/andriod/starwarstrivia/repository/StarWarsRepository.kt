package com.jacoblip.andriod.starwarstrivia.repository

import com.jacoblip.andriod.starwarstrivia.response.Character
import com.jacoblip.andriod.starwarstrivia.retrofit.RetrofitInstance
import retrofit2.Response

class StarWarsRepository {
    suspend fun getPerson(number:Int):Response<Character>{
        return RetrofitInstance.api.getCharacter(number)
    }
}