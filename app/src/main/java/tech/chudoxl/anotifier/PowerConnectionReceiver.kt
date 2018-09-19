package tech.chudoxl.anotifier

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_POWER_CONNECTED
import android.content.Intent.ACTION_POWER_DISCONNECTED
import android.content.IntentFilter
import android.os.BatteryManager
import timber.log.Timber

class PowerConnectionReceiver : BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        Timber.d("onReceive")
        if (intent == null || intent.action == null ||
                (intent.action != ACTION_POWER_CONNECTED && intent.action != ACTION_POWER_DISCONNECTED))
            return

        if (context == null)
            return
        val appContext = context.applicationContext
        val batteryReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                appContext.unregisterReceiver(this)
                val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
                val temp = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1)
                val voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1)

                val status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
                val isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL

                val chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)
                val usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB
                val acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC

                Timber.d("level=$level/$scale, temp=$temp, voltage=$voltage, status=$status, isCharging=$isCharging, usbCharge=$usbCharge, acCharge=$acCharge")
            }
        }

        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        appContext.registerReceiver(batteryReceiver, filter)
    }
}