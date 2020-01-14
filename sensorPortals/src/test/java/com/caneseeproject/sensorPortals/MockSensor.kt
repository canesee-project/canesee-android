package com.caneseeproject.sensorPortals

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
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

    override suspend fun send(encode: InputEncoder, vararg messages: SensorInput) {
        messages.forEach { echoChamber.send(encode(it)) }
    }

    @FlowPreview
    override fun readings(tokenize: ReadingTokenizer): Flow<SensorReading> =
        echoChamber.consumeAsFlow().map { tokenize(it) }

    override fun shutdown() {
        isActive = false
        echoChamber.cancel()
        println("service is shutdown")
    }

}

val mockEncode: InputEncoder = { encodedInput ->
    if (encodedInput is StringInput) encodedInput.value else throw Exception(":(")
}
val mockTokenize: ReadingTokenizer = { reading -> StringReading(reading) }