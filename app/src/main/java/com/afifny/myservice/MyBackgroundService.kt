package com.afifny.myservice

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*

class MyBackgroundService : Service() {

    companion object {
        internal  val TAG = MyBackgroundService::class.java.simpleName
    }

    //background process untuk mematikan/menghentikan services yang dijalankan dengan memanggil metode
    //stopSelf()
    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)

    override fun onBind(intent: Intent): IBinder {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand: service dijalankan")
        serviceScope.launch {
            for (i in 1..50) {
                delay(1000)
                Log.d(TAG, "do something $i")
            }
            stopSelf()
            Log.d(TAG, "service dihentikan")
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
        Log.d(TAG, "onDestroy: service dihentikan")
    }

}