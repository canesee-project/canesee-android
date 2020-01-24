package com.caneseeaproject.computervision

import com.caneseeproject.sensorPortals.SensorReading
import kotlinx.coroutines.flow.Flow
import org.json.JSONObject


interface InterCV {
    //fun sendParsedData(data: Flow<JSONObject>)
    suspend fun sendDataUp(rawData: SensorReading) //output data(in string) to the above layer
    suspend fun getDataBelow(rawData: SensorReading):Flow<String> //get data(in string) from the below layer
    //fun getParsedData():Flow<JSONObject>

}