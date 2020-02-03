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
    override var isActive: Boolean = false

    private val echoChamber: Channel<String> = Channel(10) //accept 10 events then suspend.

    override fun open() {
        isActive = true
        println("sensor is connected")
    }

    override suspend fun send(vararg messages: String) {
        messages.forEach { echoChamber.send(it) }
    }

    @FlowPreview
    override fun readings(): Flow<String> = echoChamber.consumeAsFlow()

    override fun shutdown() {
        isActive = false
        echoChamber.cancel()
        println("service is shutdown")
    }
}


/**
 *@author mhashim6 on 2019-12-08
 */
class MockSensor(override val portal: SensorPortal) : Sensor<StringInput, StringReading> {

    override fun activate() {
        portal.open()
    }

    override fun encode(signal: StringInput): String = signal.value


    override fun tokenize(raw: String): StringReading? = StringReading(raw)

    override suspend fun control(vararg signals: StringInput) {
        signals.forEach { portal.send(encode(it)) }
    }

    override fun readings(): Flow<StringReading> =
        portal.readings().map { tokenize(it) }.filterNotNull()
}
