package com.yash.myproject.networking

import com.yash.myproject.model.SuperHeroResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ISuperHerosApi {

    @GET("/v1/public/characters")
    fun fetchCharactersAsync(@Query("offset") offset: Int): Deferred<Response<SuperHeroResponse>>
}