package com.jacoblip.andriod.starwarstrivia.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.jacoblip.andriod.starwarstrivia.R


class EndGameFragment(number:Int):Fragment() {

    lateinit var restartButton: Button
    lateinit var exitButton: Button
    lateinit var resultTV: TextView
    var num = number

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.end_game_fragment,container,false)
        resultTV = view.findViewById(R.id.result_text_view)
        restartButton = view.findViewById(R.id.back_to_start_button)
        exitButton = view.findViewById(R.id.exit_game_button)

        restartButton.setOnClickListener {
            restartGame()
        }

        exitButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP //***Change Here***

            startActivity(intent)
            System.exit(0)
        }

        resultTV.text = "You Got : ${num}/10 Questions right!!!"

        return view
    }

    fun restartGame(){
        val fragmentManager: FragmentManager? = fragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager?.beginTransaction() ?: return
        val fragment = OpeningFragment.newInstance()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }


    companion object{
        fun newInstance(number: Int) =
            EndGameFragment(number)
    }
}