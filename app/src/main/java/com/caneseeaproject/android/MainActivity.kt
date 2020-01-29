package com.caneseeaproject.android

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var caneSee: CaneSeeService
    private val serviceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder) {
            caneSee = (service as CaneSeeService.CaneSeeServiceBinder).caneSee
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        connectCaneSeeService()
    }

    private fun connectCaneSeeService() {
        Intent(this, CaneSeeService::class.java).also {
            bindService(it, serviceConnection, 0)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(serviceConnection)
    }
}
