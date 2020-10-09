package com.coffeeandcookies.pokemondaywidget

import android.content.Context
import android.content.SharedPreferences

class Prefs(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, 0)

    fun getByName(name: String) = prefs.getString(name, "")

    fun setByName(key: String, value: String)
    {
        prefs.edit().putString(key, value).apply()
    }

    fun getByNameBool(name: String) = prefs.getBoolean(name, false)

    fun setByNameBool(key: String, value: Boolean)
    {
        prefs.edit().putBoolean(key, value).apply()
    }

    fun getByNameInt(name: String) = prefs.getInt(name, 0)

    fun setByNameInt(key: String, value: Int)
    {
        prefs.edit().putInt(key, value).apply()
    }

    companion object {
        private const val PREFS_NAME = "com.pokedaywidget"
        const val PREFS_GENDER_SELECTED = "prefs_gender_selected"
    }

}