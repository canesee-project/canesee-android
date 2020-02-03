package com.caneseeaproject.computervision

import com.caneseeproject.sensorPortals.SensorPortal
import kotlinx.coroutines.flow.Flow


internal class ComputerVisionInAction(override val portal: SensorPortal) : ComputerVision {

    override fun tokenize(raw: String): Vision? {
        return when {
            raw.startsWith('1') -> Vision.OCR(raw)
            else -> null // (corrupt reading, discard.) the russians did it for cv !
        }
    }

    override fun encode(signal: CVInput): String {
        return when (signal) {
            is CVInput.ModeChange -> {
                "${signal.mode}"
            }
        }
    }

    override fun visions(): Flow<Vision> = readings()
}


