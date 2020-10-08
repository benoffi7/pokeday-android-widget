package com.coffeeandcookies.pokemondaywidget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.RemoteViews
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.AppWidgetTarget
import com.bumptech.glide.request.transition.Transition
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MyAppWidgetProvider() : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // Perform this loop procedure for each App Widget that belongs to this provider
        appWidgetIds.forEach { appWidgetId ->

            // Get the layout for the App Widget and attach an on-click listener
            // to the button
            val views: RemoteViews = RemoteViews(context.packageName, R.layout.layout_widget_home)

            getPokemonDay(views, appWidgetManager, appWidgetId, context)

            // Tell the AppWidgetManager to perform an update on the current app widget

        }
    }


    private fun getPokemonDay(
        views: RemoteViews, appWidgetManager: AppWidgetManager, appWidgetId: Int, context: Context
    )
    {
        val calendar: Calendar = Calendar.getInstance()
        val day: Int = calendar.get(Calendar.DAY_OF_YEAR)

        val request = ServiceBuilder.buildService(PokeApiEndpoint::class.java)
        val call = request.getMovies(day.toString())

        call.enqueue(object : Callback<Pokemon> {
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                if (response.isSuccessful) {
                    Log.d("pokemonapi", "Pokémon del día \n${response.body().toString()}")

                    val text =
                        "Pokémon del día \n #${response.body()?.id} - ${response.body()?.name}"

                    views.setTextViewText(R.id.textView_pokemon_name, text)

                    val awt: AppWidgetTarget = object : AppWidgetTarget(
                        context.applicationContext,
                        R.id.imageView_pokemon_front, views, appWidgetId
                    ) {
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) {
                            super.onResourceReady(resource, transition)
                        }
                    }

                    var options =
                        RequestOptions().override(700, 700).placeholder(R.mipmap.ic_launcher).error(
                            R.mipmap.ic_launcher
                        )

                    Glide.with(context.applicationContext).asBitmap()
                        .load(response.body()?.sprites?.front_default).apply(
                        options
                    ).into(awt)

                    appWidgetManager.updateAppWidget(appWidgetId, views)
                }
            }

            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
            }
        })
    }
}