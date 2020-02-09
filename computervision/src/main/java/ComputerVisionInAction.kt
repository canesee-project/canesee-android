package com.caneseeaproject.computervision

import com.caneseeproject.sensorPortals.Sensor
import com.caneseeproject.sensorPortals.SensorPortal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import org.json.JSONObject


internal class ComputerVisionInAction(private val cvPortal: SensorPortal) : ComputerVision {

    private lateinit var cv: Sensor

    override fun activate() {
        cv = cvPortal.connect()
    }

    private fun cvTokenize(rawData: String): Vision? {
        val raw = JSONObject(rawData)
        return when (raw.getInt("type") == 1) {

            raw.has("value") -> Vision.OCR(raw.optString("value"))
            raw.has("value") -> Vision.Scenery(raw.optString("value"))
            raw.has("value") -> Vision.Facial(raw.optString("value"))
            raw.has("value") -> Vision.Emotion(raw.optString("value"))
            raw.has("value") -> Vision.ObjectDetection(raw.optJSONArray("value"))
            else -> null // (corrupt reading, discard.) the russians did it for cv !
        }
    }

    private fun cvEncode(processed: CVInput): String {

        return when (processed) {
            is CVInput.ModeChange -> {
                "${processed.mode.optInt("value")}"
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