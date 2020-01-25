package com.caneseeaproject.obstacledetection

import android.text.util.Rfc822Tokenizer.tokenize
import com.caneseeproject.obstacledetection.ODInput
import com.caneseeproject.obstacledetection.ODReading
import com.caneseeproject.obstacledetection.ObstacleDetection
import com.caneseeproject.sensorPortals.ReadingTokenizer
import com.caneseeproject.sensorPortals.Sensor
import com.caneseeproject.sensorPortals.SensorPortal
import com.caneseeproject.sensorPortals.SensorsTest.Companion.sensor
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*


class ObstacleDetector : ObstacleDetection {

    /**
     * Asking Bluetooth module for a cane
     */
    private val odPortal: SensorPortal = TODO()

    private var cane: Sensor

    /**
     * Connecting with this cane
     */
    fun activate(){
        cane = odPortal.connect()
    }

    /**
     * Tokenizer to convert the reading received from the cane into a high level data
     */
    private fun odReadingTokenizer(sensorRawReading : String): ODReading {
        return when {
            //assume for this case the format is: 0_3
            sensorRawReading.startsWith('0') -> ODReading.ObstacleDistance(sensorRawReading[2].toFloat())

            //assume for this case the format is: 1_3
            sensorRawReading.startsWith('1') -> ODReading.GlassesMode(sensorRawReading[2].toInt())

            //TODO: other cases.
            else -> throw Exception("possibly corrupt reading.")
        }
    }

    /**
     * Encoder to convert the high level data into a form of a string
     */
    fun odInputEncoder(highLevelInput: ODInput) : String{
        return when (highLevelInput) {
            is ODInput.RangeControl -> "${highLevelInput.percentage}"
            // TODO: other cases
            else -> throw Exception("the russians did it!")
        }
    }

    /**
     * Get data from the cane
     */
    override fun detectObstacles(): Flow<ODReading.ObstacleDistance> =
        cane
            .readings(::odReadingTokenizer)
            .filterIsInstance<ODReading.ObstacleDistance>()


    /**
     * Send data into the cane (set the cane)
     */
    override suspend fun control(what: ODInput) {
        cane.send(::odInputEncoder, what)
    }
}