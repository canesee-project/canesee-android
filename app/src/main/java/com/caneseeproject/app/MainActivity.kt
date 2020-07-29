package com.caneseeproject.app

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.speech.tts.TextToSpeech.SUCCESS
import androidx.appcompat.app.AppCompatActivity
import com.caneseeproject.computervision.*
import com.caneseeproject.tts.CaneSeeVoice
import com.caneseeproject.tts.toWhisper
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var caneSee: CaneSeeService
    private val caneSeeVoice by lazy { CaneSeeVoice.create(applicationContext) }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName) {
        }

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            caneSee = (service as CaneSeeService.CaneSeeServiceBinder).caneSee
            caneSeeVoice.start { status ->
                when (status) {
                    SUCCESS -> {
                        caneSee.caneSeeVoice = caneSeeVoice
                        caneSeeVoice.whisper("Hello!".toWhisper())
                    }
                    else -> {
                        error("tts failure")
                    }
                }
            }
//            activateDevices()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initButtons()
        connectCaneSeeService()
    }

    private fun initButtons() {
        ocr_btn.setOnClickListener {
            caneSee.controlGlasses(CVControl.ModeChange(OCR))

        }
        obj_detec.setOnClickListener {
            caneSee.controlGlasses(CVControl.ModeChange(OBJECTS))
        }
        fac_reco.setOnClickListener {
            caneSee.controlGlasses(CVControl.ModeChange(PRETTY_FACES))
        }
        emo_reco.setOnClickListener {
            caneSee.controlGlasses(CVControl.ModeChange(EMOTIONS))
        }
        scene_detect.setOnClickListener {
            caneSee.controlGlasses(CVControl.ModeChange(SCENES))
        }
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

