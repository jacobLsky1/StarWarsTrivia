package com.jacoblip.andriod.starwarstrivia.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.jacoblip.andriod.starwarstrivia.R

class OpeningFragment():Fragment() {
    lateinit var easyButton:Button
    lateinit var mediumButton:Button
    lateinit var hardButton:Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_opening, container, false)
        easyButton = view.findViewById(R.id.easy_button)
        mediumButton = view.findViewById(R.id.medium_button)
        hardButton = view.findViewById(R.id.hard_button)

        easyButton.setOnClickListener {
            startGame(1)
        }
        mediumButton.setOnClickListener {
            startGame(2)
        }
        hardButton.setOnClickListener {
            startGame(3)
        }


        return view
    }
    fun startGame(num:Int){
        val fragmentManager2: FragmentManager? = fragmentManager
        val fragmentTransaction2: FragmentTransaction = fragmentManager2?.beginTransaction() ?: return
        val fragment = TriviaFragment.newInstance(num)
        fragmentTransaction2.replace(R.id.fragment_container, fragment)
        fragmentTransaction2.commit()
    }



    companion object{
        fun newInstance() =
            OpeningFragment()
    }

}