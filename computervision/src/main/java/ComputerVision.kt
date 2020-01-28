package com.caneseeaproject.computervision

import com.caneseeproject.sensorPortals.SensorInput
import com.caneseeproject.sensorPortals.SensorReading
import kotlinx.coroutines.flow.Flow
import org.json.JSONObject


interface ComputerVision {

    fun modeChanges():Flow<CVReading.ModeChange>
    suspend fun setMode(mode: CVInput) //app detect mode of glasses

    suspend fun setMode(mode: CVInput) //app change mode of glasses

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
