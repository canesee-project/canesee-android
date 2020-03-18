
package com.caneseeaproject.computervision

import com.caneseeproject.sensorPortals.*
import kotlinx.coroutines.flow.Flow



interface ComputerVision : Sensor<Vision, CVInput> {

    fun activate()

    fun visions(): Flow<Vision>

    suspend fun setMode(mode: CVInput) //app change mode of glasses

    companion object Factory {
        fun create(sensorPortal: SensorPortal<Vision, CVInput> , cvTranslator: CVTranslator): ComputerVision =
            ComputerVisionInAction(sensorPortal , cvTranslator )
    }
}

sealed class Vision : SensorReading {
    class OCR(val transcript: String) : Vision()
    class Scenery(val scene: String) : Vision()
    class Facial (val prettyFace: String) : Vision()
    class Emotion (val emotion: String) : Vision()
    class ObjectDetection (val objects: List<DetectedObject>): Vision()
}

sealed class CVInput : SensorControl {
    class ModeChange(val mode: Int) : CVInput()
}

data class DetectedObject(val lbl:String, val pos:String)


