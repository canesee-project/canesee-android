package com.caneseeproject.app

import android.app.Service
import android.content.Intent
import android.os.Binder
import com.caneseeproject.bluetooth.BluetoothPortal
import com.caneseeproject.computervision.CVControl
import com.caneseeproject.computervision.ComputerVision
import com.caneseeproject.computervision.Vision
import com.caneseeproject.obstacledetection.ODControl
import com.caneseeproject.obstacledetection.ObstacleDetector
import com.caneseeproject.tts.CaneSeeVoice
import com.caneseeproject.tts.Whisper
import com.caneseeproject.tts.toWhisper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 *@author mhashim6 on 2020-01-28
 */
class CaneSeeService : Service(), CoroutineScope {

    private val main = CoroutineScope(Dispatchers.Main)
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO


    lateinit var caneSeeVoice: CaneSeeVoice

    private val cane: ObstacleDetector by lazy { ObstacleDetector.create(BluetoothPortal(CANE_MAC)) }
    private val glasses: ComputerVision by lazy { ComputerVision.create(BluetoothPortal(GLASSES_MAC)) }


    fun activateGlasses() {
        glasses.activate()
        debug("glasses are connected successfully.")
        caneSeeVoice.whisper("glasses are connected successfully.".toWhisper())
        useGlasses()
    }

    private fun useGlasses() = launch {
        glasses.visions().collect { vision ->
            when (vision) {
                is Vision.OCR -> outLoud(vision.transcript.toWhisper())
            }
        }
    }

    fun controlGlasses(action: CVControl) = launch {
        glasses.setMode(action)
    }

    fun activateCane() {
        cane.activate()
        debug("cane is connected successfully.")
        caneSeeVoice.whisper("cane is connected successfully.".toWhisper())
        useCane()
    }

    fun controlCane(action: ODControl) = launch {
        cane.control(action)
    }

    private fun useCane() = launch {
        cane.readings().collect { reading ->
            outLoud(reading.toWhisper())
        }
    }

    private fun outLoud(what: Whisper) = main.launch {
        caneSeeVoice.whisper(what)
    }

    override fun onBind(intent: Intent?) = CaneSeeServiceBinder()
    inner class CaneSeeServiceBinder : Binder() {
        val caneSee
            get() = this@CaneSeeService
    }

}


