package com.yash.myproject.networking

import com.yash.myproject.model.SuperHeroResponse

interface INetworkService {

    suspend fun fetchInitialSuperHeros(page:Int): NetworkRequestResult<SuperHeroResponse>
}