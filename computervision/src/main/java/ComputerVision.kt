package com.caneseeaproject.computervision

import com.caneseeproject.sensorPortals.SensorInput
import com.caneseeproject.sensorPortals.SensorReading
import kotlinx.coroutines.flow.Flow
import org.json.JSONObject


interface ComputerVision {

    fun modeChanges():Flow<CVReading.ModeChange>
    suspend fun setMode(mode: CVInput) //app detect mode of glasses

}

sealed class CVReading: SensorReading{
    class ModeChange(val mode: Int) :CVReading()
}

sealed class CVInput: SensorInput{
    class Vision(val rawData:String ): CVInput()

}