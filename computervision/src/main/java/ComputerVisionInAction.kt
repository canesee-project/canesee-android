package com.caneseeaproject.computervision

import com.caneseeproject.sensorPortals.Sensor
import com.caneseeproject.sensorPortals.SensorPortal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance


internal class ComputerVisionInAction(private val cvPortal: SensorPortal) : ComputerVision {

    private lateinit var cv: Sensor

    override fun activate() {
        cv = cvPortal.connect()
    }

    private fun cvTokenize(rawData: String): Vision {
        return when {
            rawData.startsWith('1') -> Vision.OCR(rawData)
            else -> throw Exception("the russians did it for cv !")
        }
    }

    private fun cvEncode(processed: CVInput): String {
        return when (processed) {
            is CVInput.ModeChange -> {
                "${processed.mode}"
            }
        }
    }

    override fun visions(): Flow<Vision> {
        return cv.readings(::cvTokenize).filterIsInstance()
    }


    override suspend fun setMode(mode: CVInput) {
        cv.send(::cvEncode, mode)
    }
}


