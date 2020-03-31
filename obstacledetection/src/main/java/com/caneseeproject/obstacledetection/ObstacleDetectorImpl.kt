package com.caneseeproject.obstacledetection

import com.caneseeproject.sensorPortals.PortalTranslator
import com.caneseeproject.sensorPortals.Sensor
import com.caneseeproject.sensorPortals.SensorPortal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map


internal class ObstacleDetectorImpl(private val odPortal: SensorPortal,
                                    private val odTranslator: PortalTranslator<ODReading, ODControl> = ODTranslator() )
    : ObstacleDetector {

    override fun activate() {
      odPortal.open()
    }

    override suspend fun control(vararg messages: ODControl) {
        odPortal.send(*(messages.map { odTranslator.pack(it) }.toTypedArray()))
    }

    override fun readings(): Flow<ODReading> =
        odPortal.receive().map { odTranslator.tokenize(it) }.filterNotNull()


    /**
     * Get data from the cane
     */
    override fun requireData()  : Flow<ODReading> {
        return readings()
    }


    /**
     * Send data into the cane (set the cane)
     */
    override suspend fun control(what: ODControl) {
        control(what)
    }

}

class ODTranslator : PortalTranslator<ODReading, ODControl>{

    /**
     * Encoder to convert the high level data into a form of a string
     */
    override fun pack(control: ODControl): String {
        return when (control) {
            is ODControl.RangeControl -> "${RANGE_PERCENTAGE}" + "_" + "${control.percentage}"
            // TODO: other cases
        }
    }

    /**
     * Tokenizer to convert the reading received from the cane into a high level data
     */
    override fun tokenize(reading: String): ODReading? {

        val readingValue = reading.drop(2)

        return when {
            //assume for this case the format is: 0_3
            reading.startsWith("${OBSTACLE_DISTANCE}") -> ODReading.ObstacleDistance(
                readingValue.toFloat()
            )

            //assume for this case the format is: 1_3
            reading.startsWith("${GLASSES_MODE}") -> ODReading.GlassesMode(
                readingValue.toInt()
            )

            //TODO: other cases.
            else -> null //possibly corrupt reading.
        }

    }

}