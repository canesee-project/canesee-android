package com.caneseeproject.obstacledetection

import com.caneseeproject.sensorPortals.Sensor
import com.caneseeproject.sensorPortals.SensorPortal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance


internal class ObstacleDetectorImpl(override val portal: SensorPortal) : ObstacleDetector {
    override suspend fun control(vararg signals: ODInput) {
        (this as Sensor<ODInput, ODReading>).control(*signals)
    }

    /**
     * Tokenizer to convert the reading received from the cane into a high level data
     */
    override fun tokenize(raw: String): ODReading? {
        return when {
            //assume for this case the format is: 0_3
            raw.startsWith('0') -> ODReading.ObstacleDistance(
                raw[2].toFloat()
            )

            //assume for this case the format is: 1_3
            raw.startsWith('1') -> ODReading.GlassesMode(
                raw[2].toInt()
            )

            //TODO: other cases.
            else -> null //possibly corrupt reading.
        }
    }

    /**
     * Encoder to convert the high level data into a form of a string
     */
    override fun encode(signal: ODInput): String {
        return when (signal) {
            is ODInput.RangeControl -> "${signal.percentage}"
            // TODO: other cases
        }
    }

    /**
     * Get data from the cane
     */
    override fun detectObstacles(): Flow<ODReading.ObstacleDistance> =
        readings().filterIsInstance()
}