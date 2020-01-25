package com.caneseeproject.obstacledetection

import com.caneseeproject.sensorPortals.SensorInput
import com.caneseeproject.sensorPortals.SensorReading
import org.intellij.lang.annotations.Flow

sealed class ODReading : SensorReading {

    class ObstacleDistance(val distance: Float) : ODReading()

    class GlassesMode(val mode: Int) : ODReading()

    //TODO any other type of reading
}

sealed class ODInput : SensorInput {

    class RangeControl(val percentage : Int ) : ODInput()

    //TODO any other type of input

}
interface ObstacleDetection {

    /**
     * Provides flow of high level sensor readings
     */
    suspend fun detectObstacles(): Flow<ODReading.ObstacleDistance>
    /**
     * Change the cane settings
     */
    suspend fun control(what: ODInput)
}
