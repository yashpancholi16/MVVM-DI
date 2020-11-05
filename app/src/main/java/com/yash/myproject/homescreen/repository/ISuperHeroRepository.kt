package com.yash.myproject.homescreen.repository

import androidx.lifecycle.LiveData
import com.yash.myproject.homescreen.viewmodel.ViewModelStates
import com.yash.myproject.model.SuperHeroResponse

interface ISuperHeroRepository {

    val superHeros: LiveData<SuperHeroResponse>

    val state:LiveData<ViewModelStates>

    fun fetchSuperHeros(page:Int)
}