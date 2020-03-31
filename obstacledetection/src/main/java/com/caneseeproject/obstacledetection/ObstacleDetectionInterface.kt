package com.caneseeproject.obstacledetection

import com.caneseeproject.sensorPortals.*
import kotlinx.coroutines.flow.Flow

//ODReading Constants :
const val OBSTACLE_DISTANCE: Int = 0
const val GLASSES_MODE: Int = 1

//ODControl Constants :
const val RANGE_PERCENTAGE: Int = 0


sealed class ODReading : SensorReading {

    //Represented with a '0' -> 0_distance
    class ObstacleDistance(val distance: Float) : ODReading()

    //Represented with a '1' -> 1_mode
    class GlassesMode(val mode: Int) : ODReading()

    //TODO any other type of reading
}

sealed class ODControl : SensorControl {

    //Represented with a '0' -> 0_percentage
    class RangeControl(val percentage: Int) : ODControl()

    //TODO any other type of input

}

interface ObstacleDetector : Sensor<ODReading, ODControl>{

    /**
     * Connecting with this cane
     */
    fun activate()

    /**
     * Provides flow of high level sensor readings
     */
    fun speak(): Flow<ODReading>



    /**
     * Change the cane settings
     */
    suspend fun control(what: ODControl)

    companion object Factory {
        fun create(portal: SensorPortal ,
                   translator: PortalTranslator<ODReading, ODControl> = ODTranslator())
                : ObstacleDetector = ObstacleDetectorImpl(portal, translator)
    }
}
