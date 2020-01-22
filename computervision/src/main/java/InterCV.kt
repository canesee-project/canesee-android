package com.caneseeaproject.computervision

import kotlinx.coroutines.flow.Flow
import org.json.JSONObject


interface InterCV {
    //fun sendParsedData(data: Flow<JSONObject>)
    suspend fun sendData(data: Flow<String>) //output data(in string) to the above layer
    suspend fun getData():Flow<String> //get data(in string) from the below layer
    //fun getParsedData():Flow<JSONObject>

}