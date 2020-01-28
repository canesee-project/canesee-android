package com.caneseeproject.obstacledetection

import com.caneseeproject.sensorPortals.Sensor
import com.caneseeproject.sensorPortals.SensorPortal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance


class ObstacleDetectorImpl(private val odPortal: SensorPortal) : ObstacleDetection {

    private lateinit var cane: Sensor

    override fun activate() {
        cane = odPortal.connect()
    }

    /**
     * Tokenizer to convert the reading received from the cane into a high level data
     */
    private fun odReadingTokenizer(sensorRawReading: String): ODReading {
        return when {
            //assume for this case the format is: 0_3
            sensorRawReading.startsWith('0') -> ODReading.ObstacleDistance(
                sensorRawReading[2].toFloat()
            )

            //assume for this case the format is: 1_3
            sensorRawReading.startsWith('1') -> ODReading.GlassesMode(
                sensorRawReading[2].toInt()
            )

            //TODO: other cases.
            else -> throw Exception("possibly corrupt reading.")
        }
    }

    /**
     * Encoder to convert the high level data into a form of a string
     */
    private fun odInputEncoder(highLevelInput: ODInput): String {
        return when (highLevelInput) {
            is ODInput.RangeControl -> "${highLevelInput.percentage}"
            // TODO: other cases
        }
    }

    /**
     * Get data from the cane
     */
    override fun detectObstacles(): Flow<ODReading.ObstacleDistance> =
        cane.readings(::odReadingTokenizer)
            .filterIsInstance()

    /**
     * Send data into the cane (set the cane)
     */
    override suspend fun control(what: ODInput) {
        cane.send(::odInputEncoder, what)
    }
}