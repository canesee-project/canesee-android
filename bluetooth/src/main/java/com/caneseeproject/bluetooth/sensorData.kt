package com.caneseeproject.bluetooth

import com.caneseeproject.sensorPortals.*
import kotlinx.coroutines.flow.Flow

class sensorData : Sensor{
    override fun readings(tokenize: ReadingTokenizer): Flow<SensorReading> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun send(encode: InputEncoder, vararg messages: SensorInput) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override val isActive: Boolean
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun shutdown() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }




}