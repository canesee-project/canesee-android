package com.caneseeproject.sensorPortals

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

/**
 *@author mhashim6 on 2019-12-08
 */
class MockSensorPortal : SensorPortal {
    override fun connect(): Sensor {
        println("sensor is connected")
        return MockEchoSensor()
    }
}


/**
 *@author mhashim6 on 2019-12-08
 */
class MockEchoSensor : Sensor {
    override var isActive: Boolean = true
    private val echoChamber: Channel<String> = Channel(10) //accept 10 events then suspend.

    override suspend fun <T : SensorInput> send(encode: InputEncoder<T>, vararg messages: T) {
        messages.forEach { echoChamber.send(encode(it)) }
    }

    @FlowPreview
    override fun <T : SensorReading> readings(tokenize: ReadingTokenizer<T>) =
        echoChamber.consumeAsFlow().map { tokenize(it) }.filterNotNull()

    override fun shutdown() {
        isActive = false
        echoChamber.cancel()
        println("service is shutdown")
    }

}

val mockEncode: InputEncoder<SensorInput> = { encodedInput ->
    if (encodedInput is StringInput) encodedInput.value else throw Exception(":(")
}
val mockTokenize: ReadingTokenizer<SensorReading> = { reading -> StringReading(reading) }