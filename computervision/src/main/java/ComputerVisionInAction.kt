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

    private fun cvTokenize(rawData: List<Any>): Vision? {
        val raw : String = rawData[0].toString()
        return when {

            raw.startsWith('1') -> Vision.OCR(raw)
            raw.startsWith('2') -> Vision.Scenery(raw)
            raw.startsWith('3') -> Vision.Facial(raw)
            raw.startsWith('4') -> Vision.Emotion(raw)
            raw.startsWith('5') -> Vision.ObjectDetection(rawData)
            else -> null // (corrupt reading, discard.) the russians did it for cv !
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
        //locally changed argument of //typealias ReadingTokenizer<T> = (rawReading: List<Any>) -> T?// in SensorMessages
    }


    override suspend fun setMode(mode: CVInput) {
        cv.send(::cvEncode, mode)
    }
}


