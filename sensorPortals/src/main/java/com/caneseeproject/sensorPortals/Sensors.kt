package com.caneseeproject.sensorPortals

import kotlinx.coroutines.flow.Flow


/**
 * Provides a connection to a sensor. Be it from a Bluetooth or a TCP connection.
 * @author mhashim6 on 2019-12-08
 */
interface SensorPortal {

    /**
     * Opens a connection portal to this sensor.
     */
    fun connect(): Sensor
}


/**
 * Abstract Sensor, meant to be implemented by actual sensors/ services,
 * whether they are remote or local.
 * @author mhashim6 on 2019-12-08
 */
interface Sensor {

    /**
     * Send a message to this sensor, after encoding it with the provided encoder.
     */
    suspend fun send(encode: InputEncoder, vararg messages: SensorInput)

    /**
     * Receive readings from this sensor, after tokenize-ing them with the provided tokenizer.
     */
    fun readings(tokenize: ReadingTokenizer): Flow<SensorReading>

    /**
     * Indicates whether [shutdown] has been called or not.
     */
    val isActive: Boolean

    /**
     * Shutdowns the connection.
     */
    fun shutdown()
}