package com.jacoblip.andriod.starwarstrivia.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jacoblip.andriod.starwarstrivia.repository.StarWarsRepository
import com.jacoblip.andriod.starwarstrivia.util.Constants
import com.jacoblip.andriod.starwarstrivia.viewModels.TriviaViewModel

class ViewModelProviderFactory(val repository: StarWarsRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       return TriviaViewModel(repository) as T
    }
}