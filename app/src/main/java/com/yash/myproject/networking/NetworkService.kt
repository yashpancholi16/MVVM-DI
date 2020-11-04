package com.yash.myproject.networking

import com.yash.myproject.model.SuperHeroResponse

class NetworkService(private val networkApiProvider:IApiProvider) : INetworkService {

    override suspend fun fetchSuperHeros(): NetworkRequestResult<SuperHeroResponse> {
        return networkApiProvider.getApi.fetchCharactersAsync(1).performSafeApiCallResult()
    }
}