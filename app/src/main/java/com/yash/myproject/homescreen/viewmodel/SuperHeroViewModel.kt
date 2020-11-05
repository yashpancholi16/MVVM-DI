package com.yash.myproject.homescreen.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.yash.myproject.homescreen.repository.ISuperHeroRepository

class SuperHeroViewModel(private val repository: ISuperHeroRepository) : ViewModel(),
    ISuperHeroViewModel {

    override val loaderVisibility = MutableLiveData<Boolean>()

    val superHeros = repository.superHeros
    val states = Transformations.map(repository.state)
    {
        handleState(it)
    }

    private fun handleState(state: ViewModelStates): ViewModelStates {
        when (state) {
            ViewModelStates.LOADING -> loaderVisibility.value = true
            ViewModelStates.FINISH -> loaderVisibility.value = false
        }
        return state
    }

    override fun fetchSuperHeros(page:Int) {
        repository.fetchSuperHeros(page)
    }
}