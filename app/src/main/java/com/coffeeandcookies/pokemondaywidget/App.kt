package com.coffeeandcookies.pokemondaywidget

import android.app.Application
import com.coffeeandcookies.cursokotlin.util.Prefs

class App : Application()
{
    companion object {
        lateinit var prefs: Prefs
    }

    override fun onCreate() {
        super.onCreate()
        prefs = Prefs(applicationContext)
    }

}