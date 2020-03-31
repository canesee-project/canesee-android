package com.caneseeproject.sensorPortals

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

/**
 *@author mhashim6 on 2019-12-08
 */
class EchoSensorPortal : SensorPortal {
    private val echoChamber: Channel<String> = Channel(10) //accept 10 events then suspend.

    override fun open() {
        isOpen = true
        println("portal is open")
    }

    override suspend fun send(vararg messages: String) {
        messages.forEach { echoChamber.send(it) }
    }

    @FlowPreview
    override fun receive(): Flow<String> = echoChamber.consumeAsFlow().filterNotNull()

    override var isOpen: Boolean = false

    override fun shutdown() {
        isOpen = false
        println("portal is shutdown")
    }
}


/**
 *@author mhashim6 on 2019-12-08
 */
class MockSensor(
    private val portal: SensorPortal,
    private val translator: PortalTranslator<StringReading, IntControl>
) : Sensor<StringReading, IntControl> {

    override fun activate() {
        println("sensor is activated.")
    }

    override suspend fun send(vararg messages: IntControl) {
        portal.send(*(messages.map { translator.pack(it) }.toTypedArray()))
    }

    @FlowPreview
    override fun readings(): Flow<StringReading> =
        portal.receive().map { translator.tokenize(it) }.filterNotNull()
}

class MockTranslator : PortalTranslator<StringReading, IntControl> {
    override fun pack(control: IntControl): String = control.value.toString()
    override fun tokenize(reading: String): StringReading? = StringReading(reading)
}