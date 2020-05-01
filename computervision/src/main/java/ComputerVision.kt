package com.caneseeproject.computervision

import com.caneseeproject.sensorPortals.*
import kotlinx.coroutines.flow.Flow



interface ComputerVision : Sensor<Vision, CVControl> {

    fun visions(): Flow<Vision>

    suspend fun setMode(mode: CVControl) //app change mode of glasses

    companion object Factory {
        fun create(sensorPortal: SensorPortal , cvTranslator: PortalTranslator<Vision , CVControl> = CVTranslator()): ComputerVision =
            ComputerVisionInAction(sensorPortal , cvTranslator )
    }
}

sealed class Vision : SensorReading {
    data class OCR(val transcript: String) : Vision()
    data class Scenery(val scene: String) : Vision()
    data class Facial (val prettyFace: String) : Vision()
    data class Emotion (val emotion: String) : Vision()
    data class ObjectDetection (val objects: List<DetectedObject>): Vision()
}

sealed class CVControl : SensorControl {
    class ModeChange(val mode: Int) : CVControl()
}

data class DetectedObject(val lbl:String, val pos:String)



