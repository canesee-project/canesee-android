package com.caneseeproject.obstacledetection

import com.caneseeproject.sensorPortals.SensorInput
import com.caneseeproject.sensorPortals.SensorPortal
import com.caneseeproject.sensorPortals.SensorReading
import kotlinx.coroutines.flow.Flow


sealed class ODReading : SensorReading {

    //Represented with a '0' -> 0_distance
    class ObstacleDistance(val distance: Float) : ODReading()

    //Represented with a '1' -> 1_mode
    class GlassesMode(val mode: Int) : ODReading()

    //TODO any other type of reading
}

sealed class ODInput : SensorInput {

    //Represented with a '2' -> 2_percentage
    class RangeControl(val percentage: Int) : ODInput()

    //TODO any other type of input

}

interface ObstacleDetector {

    /**
     * Connecting with this cane
     */
    fun activate()

    /**
     * Provides flow of high level sensor readings
     */
    fun detectObstacles(): Flow<ODReading.ObstacleDistance>

    /**
     * Change the cane settings
     */
    suspend fun control(what: ODInput)

    companion object Factory {
        fun create(portal: SensorPortal): ObstacleDetector = ObstacleDetectorImpl(portal)
    }
}
