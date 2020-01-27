package com.caneseeaproject.computervision

import com.caneseeproject.sensorPortals.SensorInput
import com.caneseeproject.sensorPortals.SensorReading
import kotlinx.coroutines.flow.Flow
import org.json.JSONObject


interface InterCV {

    fun sendData():Flow<cvReading.getMode>//output data(in string) to the above layer
    suspend fun setMode(mode: cvInput) //app detect mode of glasses

}

open class cvReading: SensorReading{
    class getMode(val mode: Int) :cvReading()
}

open class cvInput: SensorInput{
    class getData(val rawData:String ): cvInput()

}