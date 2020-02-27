package com.caneseeaproject.computervision

import android.util.Log
import com.caneseeproject.sensorPortals.Sensor
import com.caneseeproject.sensorPortals.SensorPortal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import org.json.JSONException
import org.json.JSONObject


internal class ComputerVisionInAction(private val cvPortal: SensorPortal) : ComputerVision {

    private lateinit var cv: Sensor

    override fun activate() {
        cv = cvPortal.connect()
    }

    private fun cvTokenize(rawData: String): Vision? {
        try {
            val raw = JSONObject(rawData)

            return when (raw.getInt("type")) {
                OCR -> Vision.OCR(raw.getString("value"))
                SCENES -> Vision.Scenery(raw.getString("value"))
                PRETTY_FACES -> Vision.Facial(raw.getString("value"))
                EMOTIONS -> Vision.Emotion(raw.getString("value"))
                OBJECTS -> raw.getJSONArray("value").run {
                    Vision.ObjectDetection(((0 until length()).map {
                        DetectedObject(
                            getJSONObject(it).getString("label"),
                            getJSONObject(it).getString("pos")
                        )
                    }))
                }
                else -> null // (corrupt reading, discard.) the russians did it for cv !
            }
        } catch (e: JSONException) {
            Log.e("CV_TOKENIZE", e.localizedMessage)
            return null
        }
    }

    private fun cvEncode(processed: CVInput): String {
        return when (processed) {
            is CVInput.ModeChange -> "0_${processed.mode}"
        }
    }

    override fun visions(): Flow<Vision> {
        return cv.readings(::cvTokenize).filterIsInstance()

    }

    override suspend fun setMode(mode: CVInput) {
        cv.send(::cvEncode, mode)
    }
}
