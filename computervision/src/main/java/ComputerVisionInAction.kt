package com.caneseeaproject.computervision

import android.util.Log
import com.caneseeproject.sensorPortals.PortalTranslator
import com.caneseeproject.sensorPortals.SensorPortal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException


internal class ComputerVisionInAction(private val cvPortal: SensorPortal,
                                       private val cvTranslator: PortalTranslator<Vision, CVControl> = CVTranslator()) : ComputerVision {


    override fun activate() {
        cvPortal.open()
    }

    override suspend fun control(vararg modes: CVControl) {
        cvPortal.send(*(modes.map { cvTranslator.pack(it) }.toTypedArray()))
    }

    override fun readings(): Flow<Vision> =
        cvPortal.receive().map { cvTranslator.tokenize(it) }.filterNotNull()

    override fun visions(): Flow<Vision> {
        return readings()

    }

    override suspend fun setMode(mode: CVControl) {
        control(mode)
    }

}

class CVTranslator : PortalTranslator<Vision, CVControl> {
    val gson = Gson()
    override fun tokenize(rawData: String): Vision? {
        try {
            val raw =  gson.fromJson(rawData, JsonObject::class.java)

            return when (raw.get("type").asInt) {
                OCR -> Vision.OCR(raw.get("value").asString)
                SCENES -> Vision.Scenery(raw.get("value").asString)
                PRETTY_FACES -> Vision.Facial(raw.get("value").asString)
                EMOTIONS -> Vision.Emotion(raw.get("value").asString)
                OBJECTS -> raw.get("value").asJsonArray.run {
                    Vision.ObjectDetection((0 until size()).map {
                        DetectedObject(
                            get(it).asJsonObject.get("label").asString,
                            get(it).asJsonObject.get("pos").asString
                        )
                    })
                }

                else -> null // (corrupt reading, discard.) the russians did it for cv !
            }
        } catch (e: JsonSyntaxException) {
            Log.e("CV_TOKENIZE", e.localizedMessage)
            return null
        }
    }

    override fun pack(processed: CVControl): String {
        return when (processed) {
            is CVControl.ModeChange -> "0_${processed.mode}"
        }
    }

    }




