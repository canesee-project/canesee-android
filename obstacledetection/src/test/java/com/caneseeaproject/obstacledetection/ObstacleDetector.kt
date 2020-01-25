package com.caneseeaproject.obstacledetection

import android.text.util.Rfc822Tokenizer.tokenize
import com.caneseeproject.obstacledetection.IObstacleDetection
import com.caneseeproject.obstacledetection.ObstacleDetectorData
import com.caneseeproject.sensorPortals.ReadingTokenizer
import com.caneseeproject.sensorPortals.Sensor
import com.caneseeproject.sensorPortals.SensorPortal
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.map


class ObstacleDetector : IObstacleDetection {

    private val mic: Channel<String> = Channel(10)

    /**
     * Asking Bluetooth module for a cane
     */
    var sensor : SensorPortal = //TODO

    var cane : Sensor

    /**
     * Connecting with this cane
     */
    fun activate(): Sensor{
        cane = sensor.connect()
        return cane
    }

    /**
     * Index of the received message from the Cane
     */
    var indexOfThisReading : Int = 0
    /**
     * Tokenizer to convert the reading received from the cane into a high level data
     */
    suspend fun odReadingTokenizer(sensorRawReading : String): ObstacleDetectorData.ODReading{
        val odReading : ObstacleDetectorData.ODReading
        odReading.readingID = indexOfThisReading
        odReading.controlType =  //TODO
        odReading.controlValue = //TODO
        indexOfThisReading ++
        return odReading
    }

    /**
     * Encoder to convert the high level data into a form of a string
     */
    suspend fun odInputEncoder(highLevelInput: ObstacleDetectorData.ODInput) : String{
        //TODO
        return String()
    }

    /**
     * Get data from the cane
     */
    override suspend fun detectObstacles() : Flow<ObstacleDetectorData.ODReading> {
        cane.readings(tokenize : odReadingTokenizer()): Flow<ODReading> =
        mic.consumeAsFlow().map { tokenize(it) }
    }

    /**
     * Send data into the cane (set the cane)
     */
    override suspend fun setOD(caneValue: ObstacleDetectorData.ODInput) {
        cane.send(encode : odInputEncoder(), vararg messages : ObstacleDetectorData.ODInput)
    }

}