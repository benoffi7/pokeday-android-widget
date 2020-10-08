package com.coffeeandcookies.pokemondaywidget

import AppWidgetAlarm
import android.app.AlarmManager
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.AppWidgetTarget
import com.coffeeandcookies.cursokotlin.util.Prefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MyAppWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        Log.d("Pokemon","onUpdate")
        for (appWidgetId in appWidgetIds)
        {
            Log.d("Pokemon","$appWidgetId")
            App.prefs.setByNameInt(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId)
            val intent =  Intent(context, AppWidgetConfigureActivity::class.java)
            val pendingIntent: PendingIntent = intent
                .let { intent ->
                    PendingIntent.getActivity(context, 0, intent, 0)
                }


            // Get the layout for the App Widget and attach an on-click listener
            // to the button
            val views: RemoteViews = RemoteViews(
                context.packageName,
                R.layout.layout_widget_home
            ).apply {
                setOnClickPendingIntent(R.id.button_configure, pendingIntent)
            }

            getPokemonDay(views, appWidgetManager, appWidgetId, context)
        }
    }

    private fun getPokemonDay(
        views: RemoteViews,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int,
        context: Context
    ) {
        val calendar: Calendar = Calendar.getInstance()
        val day: Int = calendar.get(Calendar.DAY_OF_YEAR)

        val request = ServiceBuilder.buildService(PokeApiEndpoint::class.java)
        val call = request.getPokemon(day.toString())

        call.enqueue(object : Callback<Pokemon> {
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                if (response.isSuccessful) {
                    Log.d("pokemonapi", "Pokémon del día \n${response.body().toString()}")

                    val text = context.getString(R.string.text_pokemon_dia)+"\n #${response.body()?.id} - ${response.body()?.name}"

                    views.setTextViewText(R.id.textView_pokemon_name, text)

                    val awt = object : AppWidgetTarget(
                        context.applicationContext,
                        R.id.imageView_pokemon_front,
                        views,
                        appWidgetId
                    ) {}

                    val options = RequestOptions()
                        .override(700, 700)
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)

                    val genederSelected = App.prefs.getByNameInt(Prefs.PREFS_GENDER_SELECTED)

                    var urlSelected  = ""

                    if (genederSelected == 0)
                    {
                        urlSelected = response.body()?.sprites?.front_default.toString()
                    }
                    else
                    {
                        urlSelected = response.body()?.sprites?.front_female.toString()

                        if (urlSelected.isNullOrEmpty())
                            urlSelected = response.body()?.sprites?.front_default.toString()
                    }

                    Glide.with(context.applicationContext)
                        .asBitmap()
                        .load(urlSelected)
                        .apply(options)
                        .into(awt)

                    appWidgetManager.updateAppWidget(appWidgetId, views)
                }
            }

            override fun onFailure(call: Call<Pokemon>, t: Throwable) {}
        })
    }

    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        val alarmManager = AppWidgetAlarm(context)
        alarmManager.startAlarm()
    }
}