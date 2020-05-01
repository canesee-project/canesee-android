package com.caneseeproject.android

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import com.caneseeproject.computervision.*


class MainActivity : AppCompatActivity() {

    private lateinit var caneSee: CaneSeeService
    private val serviceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName) {
        }

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            caneSee = (service as CaneSeeService.CaneSeeServiceBinder).caneSee
            activateDevices()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        connectCaneSeeService()
    }

    private fun connectCaneSeeService() {
        Intent(this, CaneSeeService::class.java).also {
            bindService(it, serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    fun activateDevices() {
        caneSee.activateGlasses()
        while (true) {
            caneSee.controlGlasses(CVControl.ModeChange(OCR))
            caneSee.controlGlasses(CVControl.ModeChange(SCENES))
            caneSee.controlGlasses(CVControl.ModeChange(PRETTY_FACES))
            caneSee.controlGlasses(CVControl.ModeChange(EMOTIONS))
            caneSee.controlGlasses(CVControl.ModeChange(OBJECTS))
//            caneSee.activateCane()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(serviceConnection)
    }
}

