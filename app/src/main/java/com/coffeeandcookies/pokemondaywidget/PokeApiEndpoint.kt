package com.coffeeandcookies.pokemondaywidget

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeApiEndpoint
{
    @GET("/api/v2/pokemon/{pokemonId}")
    fun getPokemon(@Path("pokemonId")pokemonId: String): Call<Pokemon>
}