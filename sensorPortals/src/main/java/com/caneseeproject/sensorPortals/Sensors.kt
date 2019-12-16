package com.caneseeproject.sensorPortals

import kotlinx.coroutines.flow.Flow


/**
 * Abstract Sensor, meant to be implemented by actual sensors/ services,
 * whether they are remote or local.
 * @author mhashim6 on 2019-12-08
 */
interface SensorPortal {

    /**
     * Opens a connection portal to this sense.
     */
    fun connect(): Sensor
}


/**
 * Abstract Sensor connection object. Implementations mostly rely on
 * InputStream/ OutputStream; be it from a Bluetooth or a TCP connection.
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