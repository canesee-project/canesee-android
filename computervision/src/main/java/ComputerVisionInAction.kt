package com.caneseeaproject.computervision

import android.util.Log
import com.caneseeproject.sensorPortals.PortalTranslator
import com.caneseeproject.sensorPortals.Sensor
import com.caneseeproject.sensorPortals.SensorPortal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


internal class ComputerVisionInAction(private val cvPortal: SensorPortal<Vision, CVInput>,
                                       private val cvTranslator: CVTranslator) : ComputerVision {


    override fun activate() {
        cvPortal.open()
    }

    override suspend fun send(vararg modes: CVInput) {
        cvPortal.send(*(modes.map { cvTranslator.pack(it) }.toTypedArray()))
    }

    override fun readings(): Flow<Vision> =
        cvPortal.receive().map { cvTranslator.tokenize(it) }.filterNotNull()

    override fun visions(): Flow<Vision> {
        return readings()

    }

    override suspend fun setMode(mode: CVInput) {
        send(mode)
    }

}

class CVTranslator : PortalTranslator<Vision, CVInput> {
    override fun tokenize(rawData: String): Vision? {
        try {
            val raw = JSONObject(rawData)

            return when (raw.getInt("type")) {
                OCR -> Vision.OCR(raw.getString("value"))
                SCENES -> Vision.Scenery(raw.getString("value"))
                PRETTY_FACES -> Vision.Facial(raw.getString("value"))
                EMOTIONS -> Vision.Emotion(raw.getString("value"))
                OBJECTS -> raw.getJSONArray("value").run {
                    Vision.ObjectDetection((0 until length()).map {
                        DetectedObject(
                            getJSONObject(it).getString("label"),
                            getJSONObject(it).getString("pos")
                        )
                    })
                }

                else -> null // (corrupt reading, discard.) the russians did it for cv !
            }
        } catch (e: JSONException) {
            Log.e("CV_TOKENIZE", e.localizedMessage)
            return null
        }
    }

    override fun pack(processed: CVInput): String {
        return when (processed) {
            is CVInput.ModeChange -> "0_${processed.mode}"
        }
    }

    }




