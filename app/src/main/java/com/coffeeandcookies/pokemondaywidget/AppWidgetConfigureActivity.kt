package com.coffeeandcookies.pokemondaywidget

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RemoteViews
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.coffeeandcookies.cursokotlin.util.Prefs
import kotlinx.android.synthetic.main.activity_widget_configure.*

class AppWidgetConfigureActivity : AppCompatActivity()
{
    private var genderSelected : Int = 0
    private var  appWidgetId : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_widget_configure)

        setResult(RESULT_CANCELED);

         genderSelected = App.prefs.getByNameInt(Prefs.PREFS_GENDER_SELECTED)!!

        appWidgetId = App.prefs.getByNameInt(AppWidgetManager.EXTRA_APPWIDGET_ID)!!

        if (genderSelected == 0)
        {
            changeSelected(genderSelected,ll_male,ll_female,imageView_male,imageView_female)
        }
        else
        {
            changeSelected(genderSelected,ll_female,ll_male,imageView_female,imageView_male)
        }

        ll_male.setOnClickListener{

            changeSelected(0,ll_male,ll_female,imageView_male,imageView_female)
        }

        ll_female.setOnClickListener{

            changeSelected(1,ll_female,ll_male,imageView_female,imageView_male)
        }

        button_save.setOnClickListener {

            val intent = Intent(this, MyAppWidgetProvider::class.java)
            intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
            val ids = AppWidgetManager.getInstance(application).getAppWidgetIds(ComponentName(
                applicationContext,MyAppWidgetProvider::class.java!!))
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
            sendBroadcast(intent)
            finish()
        }
    }

    private fun changeSelected(genderSelected : Int,
                               linearLayoutSelected: LinearLayout, linearLayoutDesSelected: LinearLayout,
                               imageViewSelected: ImageView, imageViewDesSelected: ImageView) {
        App.prefs.setByNameInt(Prefs.PREFS_GENDER_SELECTED,genderSelected)
        this.genderSelected = genderSelected

        linearLayoutSelected.setBackgroundColor(getColor(R.color.colorPrimary_selected))
        linearLayoutDesSelected.setBackgroundColor(getColor(android.R.color.transparent))

        var drawableSelected = R.drawable.nidoran_male
        var drawableDesSelected = R.drawable.nidora_female

        if (genderSelected == 1) {

            drawableSelected = R.drawable.nidora_female
            drawableDesSelected = R.drawable.nidoran_male
        }

            Glide.with(this)
                .asGif()
                .load(drawableSelected)
                .into(imageViewSelected)

        imageViewDesSelected.setImageDrawable(getDrawable(drawableDesSelected))

    }
}