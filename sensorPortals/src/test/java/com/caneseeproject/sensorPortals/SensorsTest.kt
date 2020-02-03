package com.caneseeproject.sensorPortals

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
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
        lateinit var portal: SensorPortal
        lateinit var sensor: Sensor<StringInput, StringReading>

        @BeforeClass
        @JvmStatic
        fun initTest() {
            portal = EchoSensorPortal()
            sensor = MockSensor(portal)
            sensor.activate()
        }
    }


    @Test
    fun testSensorConnection() {
        assert(portal.isActive)
    }

    @Test
    fun testSensorIncomingMessages() {
        runBlocking {

            launch {
                sensor.readings().take(2)
                    .filterIsInstance<StringReading>()
                    .map { it.value }
                    .reduce { acc, s -> acc + s }
                    .also { println(it); assert(it == "Hello World") }
            }

            sensor.control("Hello".toInput(), " World".toInput())

            delay(2000)
            portal.shutdown()
            assert(portal.isActive.not())
        }
    }
}
