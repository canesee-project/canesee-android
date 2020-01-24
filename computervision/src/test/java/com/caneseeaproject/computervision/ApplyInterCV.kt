package com.caneseeaproject.computervision

import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.*
import com.caneseeproject.sensorPortals.MockEchoSensor
import com.caneseeproject.sensorPortals.SensorReading

class ApplyInterCV: InterCV {
    private val channel = Channel<Flow<String>>()

    //convert from Flow<sensorReading> to Flow<String>
    fun emitter(sensor_reading : Flow<SensorReading>): Flow<String> =
        { sensor_reading.toString() }
            .asFlow()

    //getting data from sensorPortal
    override suspend fun getDataBelow(rawData: SensorReading):Flow<String>{
        val MockEchoSensor =  MockEchoSensor()
        val received = emitter( MockEchoSensor.readings { rawData })
        return received
    }


    //send data to channel opened between CV and App
    override suspend fun sendDataUp(rawData: SensorReading) {
        channel.send(getDataBelow(rawData))
    }

}