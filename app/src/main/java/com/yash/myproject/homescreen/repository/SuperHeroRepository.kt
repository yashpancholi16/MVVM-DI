package com.yash.myproject.homescreen.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yash.myproject.homescreen.viewmodel.ViewModelStates
import com.yash.myproject.model.SuperHeroResponse
import com.yash.myproject.networking.IApiProvider
import com.yash.myproject.networking.INetworkService
import com.yash.myproject.networking.NetworkRequestResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SuperHeroRepository(private val networkProvider: INetworkService) : ISuperHeroRepository {

    private val mStates = MutableLiveData<ViewModelStates>()
    override val state: LiveData<ViewModelStates> = mStates

    private val mSuperHeros = MutableLiveData<SuperHeroResponse>()
    override val superHeros: LiveData<SuperHeroResponse> = mSuperHeros

    override fun fetchSuperHeros() {
        mStates.value = ViewModelStates.LOADING
        GlobalScope.launch(Dispatchers.IO) {
            val result = networkProvider.fetchSuperHeros()

            GlobalScope.launch(Dispatchers.Main) {
                when (result) {
                    is NetworkRequestResult.Success -> {
                        mSuperHeros.value = result.data
                    }
                    else -> Unit
                }
                mStates.value = ViewModelStates.FINISH
            }
        }
    }
}