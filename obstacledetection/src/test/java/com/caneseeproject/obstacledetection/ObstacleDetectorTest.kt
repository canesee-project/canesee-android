package com.caneseeproject.obstacledetection

import org.junit.Test

class ObstacleDetectorTest {
    private val testODTanslator = ODTranslator()
    lateinit var packagedMessage : String
    var rangeControlTest = ODControl.RangeControl(50)

    @Test
    fun testTokenizingObstacleDistance() {
        val tokenizedMessage = testODTanslator.tokenize("${OBSTACLE_DISTANCE}_6.5")
        println(tokenizedMessage)
        assert(tokenizedMessage is ODReading.ObstacleDistance && tokenizedMessage.distance == 6.5f)
    }

    @Test
    fun testTokenizingGlassesMode() {
        val tokenizedMessage = testODTanslator.tokenize("${GLASSES_MODE}_2")
        println(tokenizedMessage)
        assert(tokenizedMessage is ODReading.GlassesMode && tokenizedMessage.mode == 2)
    }

    @Test
    fun testPackagingControl() {
        packagedMessage = testODTanslator.pack(rangeControlTest)
        println(packagedMessage)
        assert(packagedMessage == "0_50")
    }

}