package com.caneseeaproject.computervision

import com.caneseeproject.sensorPortals.Sensor
import com.caneseeproject.sensorPortals.SensorInput
import com.caneseeproject.sensorPortals.SensorPortal
import com.caneseeproject.sensorPortals.SensorReading
import kotlinx.coroutines.flow.Flow


interface ComputerVision : Sensor<CVInput, Vision> {

    fun visions(): Flow<Vision>

    companion object Factory {
        fun create(sensorPortal: SensorPortal): ComputerVision =
            ComputerVisionInAction(sensorPortal)
    }
}

sealed class Vision : SensorReading {
    class OCR(val transcript: String) : Vision()
}

sealed class CVInput : SensorInput {
    class ModeChange(val mode: Int) : CVInput()

}
