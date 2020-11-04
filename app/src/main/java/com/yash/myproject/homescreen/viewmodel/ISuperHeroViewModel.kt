package com.yash.myproject.homescreen.viewmodel

import androidx.lifecycle.LiveData

interface ISuperHeroViewModel {

    val loaderVisibility: LiveData<Boolean>

    fun fetchSuperHeros()
}