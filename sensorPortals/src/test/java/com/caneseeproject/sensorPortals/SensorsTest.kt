package com.caneseeproject.sensorPortals

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
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
        lateinit var sensor: Sensor<StringReading, IntControl>
        lateinit var portal: SensorPortal

        @BeforeClass
        @JvmStatic
        fun initTest() {
            portal = EchoSensorPortal().apply {
                open()
            }
            sensor = MockSensor(portal, MockTranslator())
        }
    }

    @Test
    fun testSensorConnection() {
        assert(portal.isOpen)
        sensor.activate()
    }

    @Test
    fun testSensorIncomingMessages() {
        runBlocking {
            launch {
                sensor.readings().take(2)
                    .map { it.value }
                    .reduce { acc, s -> acc + s }
                    .also { println(it); assert(it == "5566") }
            }

            sensor.control(55.toControl(), 66.toControl())

            delay(2000)
            portal.shutdown()
            assert(portal.isOpen.not())
        }
    }
}
