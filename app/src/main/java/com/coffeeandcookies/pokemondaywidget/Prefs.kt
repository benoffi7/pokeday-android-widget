package com.coffeeandcookies.cursokotlin.util

import android.content.Context
import android.content.SharedPreferences

class Prefs(context: Context)
{
    companion object
    {
        val PREFS_NAME = "com.pokedaywidget"
        val PREFS_GENDER_SELECTED = "prefs_gender_selected"
    }

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, 0)

    fun getByName(name : String) : String?
    {
        return prefs.getString(name,"")
    }

    fun setByName(key : String, value :String)
    {
        prefs.edit().putString(key, value).apply()
    }

    fun getByNameBool(name : String) : Boolean
    {
        return prefs.getBoolean(name,false)
    }

    fun setByNameBool(key : String, value :Boolean)
    {
        prefs.edit().putBoolean(key, value).apply()
    }

    fun getByNameInt(name : String) : Int?
    {
        return prefs.getInt(name,0)
    }

    fun setByNameInt(key : String, value :Int)
    {
        prefs.edit().putInt(key, value).apply()
    }

}