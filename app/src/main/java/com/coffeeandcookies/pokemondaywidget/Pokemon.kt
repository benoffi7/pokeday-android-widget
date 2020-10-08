package com.coffeeandcookies.pokemondaywidget

data class Pokemon(val id: Int, val name: String, val sprites : Sprites)
{
    data class Sprites(
        val front_default
        : String
    )
}