package tech.chudoxl.anotifier

import android.app.Application
import timber.log.Timber

class TheApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(FileLoggingTree(this))
    }
}