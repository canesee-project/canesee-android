package com.caneseeproject.sensorPortals

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.BeforeClass
import org.junit.Test

/**
 * @author mhashim6 on 2019-12-08
 */
@ExperimentalCoroutinesApi
class SensorsTest {

    companion object {
        lateinit var sensor: Sensor

        @BeforeClass
        @JvmStatic
        fun initTest() {
            sensor = MockSensorPortal().connect()
        }
    }


    @Test
    fun testSensorConnection() {
        assert(sensor.isActive)
    }

    @Test
    fun testSensorIncomingMessages() {
        runBlocking {

            launch {
                sensor.readings().take(2).reduce { acc, s -> acc + s }.also {
                    assert(it == "Hello World")
                }
            }

            sensor.send("Hello", " World")

            delay(2000)
            sensor.shutdown()
            assert(sensor.isActive.not())
        }
    }
}
