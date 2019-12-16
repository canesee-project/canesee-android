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
     * Send a message to this sensor.
     */
    suspend fun send(vararg messages: String)

    /**
     * Receive readings from this sensor.
     */
    suspend fun readings(): Flow<String>

    /**
     * Indicates whether [shutdown] has been called or not.
     */
    val isActive: Boolean

    /**
     * Shutdowns the connection.
     */
    fun shutdown()
}