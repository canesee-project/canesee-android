
package com.caneseeaproject.computervision

import com.caneseeproject.sensorPortals.SensorInput
import com.caneseeproject.sensorPortals.SensorPortal
import com.caneseeproject.sensorPortals.SensorReading
import kotlinx.coroutines.flow.Flow



interface ComputerVision {

    fun activate()

    fun visions(): Flow<Vision>

    suspend fun setMode(mode: CVInput) //app change mode of glasses

    companion object Factory {
        fun create(sensorPortal: SensorPortal): ComputerVision =
            ComputerVisionInAction(sensorPortal)
    }
}

sealed class Vision : SensorReading {
    class OCR(val transcript: String) : Vision()
    class Scenery(val scene: String) : Vision()
    class Facial (val prettyFace: String) : Vision()
    class Emotion (val emotion: String) : Vision()
    class ObjectDetection (val objects: List<String>): Vision()
}

sealed class CVInput : SensorInput {
    class ModeChange(val mode: Int) : CVInput()




}
