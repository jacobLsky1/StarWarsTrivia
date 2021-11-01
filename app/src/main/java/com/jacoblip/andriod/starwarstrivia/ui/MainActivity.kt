package com.jacoblip.andriod.starwarstrivia.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jacoblip.andriod.starwarstrivia.R
import com.jacoblip.andriod.starwarstrivia.repository.StarWarsRepository
import com.jacoblip.andriod.starwarstrivia.ui.fragments.OpeningFragment
import com.jacoblip.andriod.starwarstrivia.viewModels.TriviaViewModel

class MainActivity : AppCompatActivity() {


    lateinit var openingFragment: Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val isFragmentContainerEmpty = savedInstanceState == null
        if (isFragmentContainerEmpty) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container,
                    OpeningFragment.newInstance())
                .commit()
        }

    }


}