package com.caneseeproject.sensorPortals

/**
 *@author mhashim6 on 3/17/20
 */
interface PortalTranslator<R : SensorReading, C : SensorControl> {
    /**
     * Converts a project-defined higher-level control signal to a raw representation,
     * suitable for sending over a portal.
     */
    fun pack(control: C): String

    /**
     * Converts the sensor's reading from its raw form to a project-defined higher-level object.
     * Might return null in case of corrupt readings.
     */
    fun tokenize(reading: String): R?
}
