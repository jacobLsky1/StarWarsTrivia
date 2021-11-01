package com.jacoblip.andriod.starwarstrivia.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jacoblip.andriod.starwarstrivia.repository.StarWarsRepository
import com.jacoblip.andriod.starwarstrivia.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import com.jacoblip.andriod.starwarstrivia.response.Character
import com.jacoblip.andriod.starwarstrivia.ui.fragments.TriviaFragment
import com.jacoblip.andriod.starwarstrivia.util.Constants
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

private const val TAG  = "TriviaViewModel"
class TriviaViewModel(val repository: StarWarsRepository):ViewModel(){

    var starWarsCharacter: MutableLiveData<Resource<Character>> = MutableLiveData()
    var webResponse:Character? = null
    var characterList:MutableList<String> = mutableListOf()
    var QuestionNumber = 1
    var numberOfCorrectAnswers = 0
    var listOfCharacters:ArrayList<Int>  = arrayListOf()
    var characterNumber = 1
    var levelOfDifficalty = Constants.levelOfDifficalty

    init {
            sendRequest()
    }

    fun sendRequest(){
        levelOfDifficalty = Constants.levelOfDifficalty
        characterNumber = retriveCharacterNumber()
        getStarWarsCharacter()
    }


    fun getStarWarsCharacter() = viewModelScope.launch {
        Log.i(TAG,"fun started")
        starWarsCharacter.postValue(Resource.Loading())
        Log.i(TAG,"resource loding")
        val response = repository.getPerson(characterNumber)
        Log.i(TAG,"request made")
        starWarsCharacter.postValue(handleResponse(response))
        Log.i(TAG,"value posted")
    }

    private fun handleResponse(response: Response<Character>):Resource<Character>{
        if(response.isSuccessful){
            response.body()?.let { resultResponse->
                    webResponse = resultResponse
                return Resource.Success(webResponse?:resultResponse)
            }
        }
        return Resource.Error(response.message())
    }


    fun retriveCharacterNumber():Int{
        var num = 0
        when(levelOfDifficalty){
            1->{
                num = Random.nextInt(1,15)
                while (listOfCharacters.contains(num))
                    num = Random.nextInt(1,15)
            }
            2->{
                num = Random.nextInt(16,30)
                while (listOfCharacters.contains(num))
                    num = Random.nextInt(16,30)
            }
            3->{
                num = Random.nextInt(31,50)
                while (listOfCharacters.contains(num))
                    num = Random.nextInt(31,50)
            }

        }
        return num
    }
}