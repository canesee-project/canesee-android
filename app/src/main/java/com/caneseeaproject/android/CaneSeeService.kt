package com.caneseeaproject.android

import android.app.Service
import android.content.Intent
import android.os.Binder
import com.caneseeaproject.computervision.CVInput
import com.caneseeaproject.computervision.ComputerVision
import com.caneseeaproject.computervision.Vision
import com.caneseeproject.bluetooth.BluetoothPortal
import com.caneseeproject.obstacledetection.ODInput
import com.caneseeproject.obstacledetection.ObstacleDetector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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


    private val cane: ObstacleDetector by lazy { ObstacleDetector.create(BluetoothPortal(CANE_MAC)) }
    private val glasses: ComputerVision by lazy { ComputerVision.create(BluetoothPortal(GLASSES_MAC)) }


    fun activateGlasses() {
        glasses.activate()
        debug("glasses are connected successfully.")
        useGlasses()
    }

    private fun useGlasses() = launch {
        glasses.visions().collect { vision ->
            when (vision) {
                is Vision.OCR -> outLoud(vision.transcript)
            }
        }
    }

    fun controlGlasses(action: CVInput) = launch {
        glasses.setMode(action)
    }

    fun activateCane() {
        cane.activate()
        debug("cane is connected successfully.")
        useCane()
    }

    fun controlCane(action: ODInput) = launch {
        cane.control(action)
    }

    private fun useCane() = launch {
        cane.detectObstacles().collect { obstacle -> outLoud(obstacle.distance.toString()) }
    }

    private fun outLoud(what: String) = main.launch {
        //TODO: replace with TTS.
        debug(what)
    }

    override fun onBind(intent: Intent?) = CaneSeeServiceBinder()
    inner class CaneSeeServiceBinder : Binder() {
        val caneSee
            get() = this@CaneSeeService
    }

}


