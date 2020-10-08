import android.app.AlarmManager
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import com.coffeeandcookies.pokemondaywidget.MyAppWidgetProvider
import com.coffeeandcookies.pokemondaywidget.R
import java.util.*

class AppWidgetAlarm(private val context: Context?) {

    private val INTERVAL_MILLIS : Long = 1000 * 5

    fun startAlarm() {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val thisWidget = ComponentName(
            context,
            MyAppWidgetProvider::class.java
        )
        val manager = AppWidgetManager.getInstance(context)
        manager.updateAppWidget(
            thisWidget,
            RemoteViews(
                context.packageName,
                R.layout.layout_widget_home
            )
        )
        val appWidgetIds = manager.getAppWidgetIds(thisWidget)
        val alarm = Intent(context, MyAppWidgetProvider::class.java)
        alarm.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        alarm.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds)
        val pendingIntent = PendingIntent.getBroadcast(
            context, 0, alarm,
            PendingIntent.FLAG_CANCEL_CURRENT
        )
        alarmManager.setRepeating(
            AlarmManager.RTC, System.currentTimeMillis(),
            INTERVAL_MILLIS, pendingIntent
        )
    }


}