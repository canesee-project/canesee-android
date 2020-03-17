package com.caneseeproject.sensorPortals

import kotlinx.coroutines.flow.Flow


/**
 * Provides a connection to a sensor. Be it from a Bluetooth or a TCP connection.
 * @author mhashim6 on 2019-12-08
 */
interface SensorPortal<R : SensorReading, C : SensorControl> {

    /**
     * Opens the portal.
     */
    fun open()

    /**
     * Send a message through the portal.
     */
    suspend fun send(vararg messages: String)

    /**
     * receive a flow of messages through the portal.
     */
    fun receive(): Flow<String>

    /**
     * Indicates whether the portal is open or not.
     */
    val isOpen: Boolean

    /**
     * Shuts down the portal.
     */
    fun shutdown()
}


/**
 * Abstract Sensor, meant to be implemented by actual sensors/ services,
 * whether they are remote or local.
 * @author mhashim6 on 2019-12-08
 */
interface Sensor<R : SensorReading, C : SensorControl> {

    /**
     * Send a message to this sensor, after encoding it with the provided encoder.
     */
    suspend fun send(vararg messages: C)

    /**
     * Receive readings from this sensor, after tokenize-ing them with the provided tokenizer.
     */
    fun readings(): Flow<R>
}