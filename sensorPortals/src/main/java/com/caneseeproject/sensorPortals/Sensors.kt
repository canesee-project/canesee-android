package com.caneseeproject.sensorPortals

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map


/**
 * Provides a connection to a sensor. Be it from a Bluetooth or a TCP connection.
 * @author mhashim6 on 2019-12-08
 */
interface SensorPortal {

    /**
     * Opens a connection portal to this sensor.
     */
    fun open()

    /**
     * Send a message through this portal.
     */
    suspend fun send(vararg messages: String)

    /**
     * Receive readings through this portal.
     */
    fun readings(): Flow<String>

    /**
     * Indicates whether [shutdown] has been called or not.
     */
    val isActive: Boolean

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
interface Sensor<I : SensorInput, O : SensorReading> {

    val portal: SensorPortal

    /**
     * Activate this sensor.
     */
    fun activate() {
        portal.open()
    }

    /**
     * Converts a project-defined higher-level control signal to a raw form,
     * suitable for sending over a portal.
     */
    fun encode(signal: I): String

    /**
     * Converts the sensor's reading from its raw form to a project-defined higher-level object.
     * Might return null in case of corrupt reading.
     */
    fun tokenize(raw: String): O?

    suspend fun control(vararg signals: I) {
        portal.send(*signals.map { encode(it) }.toTypedArray())
    }

    /**
     * Receive readings from this sensor, after tokenize-ing them with the provided tokenizer.
     */
    fun readings(): Flow<O> = portal.readings().map { tokenize(it) }.filterNotNull()


}