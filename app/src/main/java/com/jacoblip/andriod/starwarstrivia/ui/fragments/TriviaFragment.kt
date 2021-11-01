package com.jacoblip.andriod.starwarstrivia.ui.fragments

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jacoblip.andriod.starwarstrivia.R
import com.jacoblip.andriod.starwarstrivia.repository.StarWarsRepository
import com.jacoblip.andriod.starwarstrivia.response.Character
import com.jacoblip.andriod.starwarstrivia.ui.ViewModelProviderFactory
import com.jacoblip.andriod.starwarstrivia.util.Constants
import com.jacoblip.andriod.starwarstrivia.util.Resource
import com.jacoblip.andriod.starwarstrivia.viewModels.TriviaViewModel
import kotlin.random.Random

private const val TAG = "TriviaFragment"

class TriviaFragment(number:Int):Fragment() {
    lateinit var viewModel : TriviaViewModel
    lateinit var submitButton: Button
    lateinit var characterName:TextView
    lateinit var imageButton1: ImageButton
    lateinit var imageButton2: ImageButton
    lateinit var imageButton3: ImageButton
    lateinit var imageButton1TV:TextView
    lateinit var imageButton2TV:TextView
    lateinit var imageButton3TV:TextView
    lateinit var questionNumberTv:TextView
    lateinit var progressBar: ProgressBar
    var listOfFilms = listOf<String>()
    lateinit var filmsHashMap:LinkedHashMap<String,Int>
    lateinit var images:Array<ImageButton>
    lateinit var imageTVs:Array<TextView>
    var filmNames :Array<String> = arrayOf("A New Hope","The Empire Strikes Back","Return Of The Jedi","The Phantom Menece","Attack Of The Clones","Revenge Of The Sith")
    var difficaltyLevel = number
    var solutionForCharacter :ArrayList<Boolean> = arrayListOf(false,false,false)
    var myAnswer :ArrayList<Boolean> = arrayListOf(false,false,false)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        Constants.levelOfDifficalty = difficaltyLevel
        val view = inflater.inflate(R.layout.fragment_trivia,container,false)
        submitButton = view.findViewById(R.id.submit_button)
        characterName = view.findViewById(R.id.character_name_TV)
        imageButton1 = view.findViewById(R.id.imageButton1)
        imageButton2 = view.findViewById(R.id.imageButton2)
        imageButton3 = view.findViewById(R.id.imageButton3)
        imageButton1TV = view.findViewById(R.id.imageButton1TV)
        imageButton2TV = view.findViewById(R.id.imageButton2TV)
        imageButton3TV = view.findViewById(R.id.imageButton3TV)
        progressBar = view.findViewById(R.id.progressBar)
        questionNumberTv = view.findViewById(R.id.questionNumberTV)

        filmsHashMap = linkedMapOf()
        filmsHashMap.put("3",R.drawable.the_phantom_menece)
        filmsHashMap.put("4",R.drawable.attack_of_the_clones)
        filmsHashMap.put("5",R.drawable.revenge_of_the_sith)
        filmsHashMap.put("0",R.drawable.a_new_hope)
        filmsHashMap.put("1",R.drawable.the_empire_strikes_back)
        filmsHashMap.put("2",R.drawable.return_of_the_jedi)
        images= arrayOf(imageButton1,imageButton2,imageButton3)
        imageTVs= arrayOf(imageButton1TV,imageButton2TV,imageButton3TV)

        val repository = StarWarsRepository()
        val viewModelProviderFactory = ViewModelProviderFactory(repository)
        viewModel = ViewModelProvider(this,viewModelProviderFactory).get(TriviaViewModel::class.java)

        submitButton.setOnClickListener {
            submitAnswer()
        }
        imageButton1.setOnClickListener {
            myAnswer[0] = !myAnswer[0]
            setButtonColor(myAnswer[0],0)
        }
        imageButton2.setOnClickListener {
            myAnswer[1] = !myAnswer[1]
            setButtonColor(myAnswer[1],1)
        }
        imageButton3.setOnClickListener {
            myAnswer[2] = !myAnswer[2]
            setButtonColor(myAnswer[2],2)
        }
        viewModel.starWarsCharacter.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Resource.Success -> {
                    progressBar.visibility = View.GONE
                    response.data?.let { character ->
                        characterName.text = character.name
                        listOfFilms = character.films
                        questionNumberTv.text = "${viewModel.QuestionNumber}/10"
                        setFilms(character)
                    }
                }
                is Resource.Error -> {
                    progressBar.visibility = View.VISIBLE
                    response.message?.let { message ->
                        Log.e(TAG, "An error occured: $message")
                    }
                }
                is Resource.Loading -> {
                    progressBar.visibility = View.VISIBLE
                }
            }
        })
        return view
    }

    fun submitAnswer(){
        if(viewModel.QuestionNumber==10){
            val fragmentManager: FragmentManager? = fragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager?.beginTransaction() ?: return
            val fragment = EndGameFragment.newInstance(viewModel.numberOfCorrectAnswers)
            fragmentTransaction.replace(R.id.fragment_container, fragment)
            fragmentTransaction.commit()
        }
        else{
            checkAnswer()
            viewModel.sendRequest()
        }
    }



    fun setFilms(character:Character){

        listOfFilms = character.films
        var films:ArrayList<String>  = arrayListOf()
        for(film in listOfFilms){
            films.add(film[film.length-2].toString())
        }
        var nums = arrayListOf<Int>()
        for(i in 0..2){
            var j = Random.nextInt(0, 6)
            while (nums.contains(j)) {
                 j = Random.nextInt(0, 6)
            }
            nums.add(j)
            filmsHashMap[j.toString()]?.let { images[i].setImageResource(it) }
            imageTVs[i].text = filmNames[j]
            solutionForCharacter[i] = films.contains((j+1).toString())
        }

        for(i in 0..2) {
            myAnswer[i] = false
            setButtonColor(false,i)
        }
    }

    fun setButtonColor(pressed:Boolean,number: Int){
        if(pressed)
            imageTVs[number].setTextColor(Color.parseColor("#AFA126"))
        else
            imageTVs[number].setTextColor(Color.parseColor("#000000"))
    }

    fun checkAnswer(){
        if(solutionForCharacter==myAnswer)
            viewModel.numberOfCorrectAnswers++

        viewModel.QuestionNumber++
    }

    companion object{
        fun newInstance(number: Int) =
            TriviaFragment(number)
    }

}