package com.caneseeproject.sensorPortals

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow

/**
 *@author mhashim6 on 2019-12-08
 */
class MockSensorPortal : SensorPortal {
    override fun connect(): Sensor {
        println("service is connected")
        return MockEchoSensor()
    }
}


/**
 *@author mhashim6 on 2019-12-08
 */
class MockEchoSensor : Sensor {
    override var isActive: Boolean = true
    private val echoChamber: Channel<String> = Channel(10) //accept 10 events then suspend.

    override suspend fun send(vararg messages: String) {
        messages.forEach { echoChamber.send(it) }
    }

    @FlowPreview
    override suspend fun readings(): Flow<String> {
        return echoChamber.consumeAsFlow()
    }

    override fun shutdown() {
        isActive = false
        echoChamber.cancel()
        println("service is shutdown")
    }

}