package com.caneseeaproject.obstacledetection

import androidx.lifecycle.Transformations.map
import com.caneseeproject.obstacledetection.*
import org.junit.Test
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.filterIsInstance

class ObstacleDetectorTest {
    private val testODTanslator = ODTranslator()
    private var tokenizedMessage : ODReading? = null

    lateinit var packagedMessage : String
    var rangeControlTest = ODControl.RangeControl(50)

    @Test
    fun testTokenizingObstacleDistance() {
        tokenizedMessage = testODTanslator.tokenize("${OBSTACLE_DISTANCE}_6.5")
        println(tokenizedMessage)
        assert(tokenizedMessage != null && tokenizedMessage is ODReading.ObstacleDistance)
    }

    @Test
    fun testTokenizingGlassesMode() {
        tokenizedMessage = testODTanslator.tokenize("${GLASSES_MODE}_2")
        println(tokenizedMessage)
        assert(tokenizedMessage != null && tokenizedMessage is ODReading.GlassesMode)
    }

    @Test
    fun testPackagingControl() {
        packagedMessage = testODTanslator.pack(rangeControlTest)
        println(packagedMessage)
        assert(packagedMessage == "0_50")
    }

}