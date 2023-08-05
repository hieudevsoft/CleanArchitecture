package hieubui.eup.base_clean_architecture.app

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDexApplication

class MyApplication: MultiDexApplication() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onTerminate() {
        super.onTerminate()
    }
}