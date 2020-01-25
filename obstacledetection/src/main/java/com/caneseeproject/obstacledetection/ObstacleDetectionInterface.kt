package com.caneseeproject.obstacledetection

import com.caneseeproject.sensorPortals.SensorInput
import com.caneseeproject.sensorPortals.SensorReading
import org.intellij.lang.annotations.Flow

sealed class ObstacleDetectorData{

    /**
     * Obstacle Detector sensor reading
     */
    class ODReading(var readingID : Int ,var controlType : String , var controlValue : Int) : SensorReading{}

    /**
     * Obstacle Detector sensor input
     */
    class ODInput(var inputID : Int , var controlType : String , var controlValue : Int) : SensorInput{}
}

interface IObstacleDetection {

    /**
     * Provides flow of high level sensor readings
     */
    suspend fun detectObstacles(): Flow<ObstacleDetectorData.ODReading>

    /**
     * Change the cane settings
     */
    suspend fun setOD(caneValue: ObstacleDetectorData.ODInput) : Flow<String>

}
