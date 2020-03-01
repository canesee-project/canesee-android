package com.caneseeproject.obstacledetection

import com.caneseeproject.sensorPortals.Sensor
import com.caneseeproject.sensorPortals.SensorPortal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance


internal class ObstacleDetectorImpl(private val odPortal: SensorPortal) : ObstacleDetector {

    private lateinit var cane: Sensor

    override fun activate() {
        cane = odPortal.connect()
    }

    /**
     * Tokenizer to convert the reading received from the cane into a high level data
     */
    private fun odReadingTokenizer(sensorRawReading: String): ODReading? {

        val readingValue = sensorRawReading.drop(2)

        return when {
            //assume for this case the format is: 0_3
            sensorRawReading.startsWith('0') -> ODReading.ObstacleDistance(
                readingValue.toFloat()
            )

            //assume for this case the format is: 1_3
            sensorRawReading.startsWith('1') -> ODReading.GlassesMode(
                readingValue.toInt()
            )

            //TODO: other cases.
            else -> null //possibly corrupt reading.
        }
    }

    /**
     * Encoder to convert the high level data into a form of a string
     */
    private fun odInputEncoder(highLevelInput: ODInput): String {
        return when (highLevelInput) {
            is ODInput.RangeControl -> "2_${highLevelInput.percentage}"
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