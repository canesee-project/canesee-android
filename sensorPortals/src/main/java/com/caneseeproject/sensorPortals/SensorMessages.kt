package com.caneseeproject.sensorPortals

/**
 *@author mhashim6 on 2020-01-14
 */


/**
 * Abstracts a sensor reading, meant to be implemented to define different types of readings.
 */
interface SensorReading
/**
 * converts the sensor's reading from its raw form to a project-defined higher-level object.
 */
typealias ReadingTokenizer = (rawReading: String) -> SensorReading

/**
 * Dummy SensorReading for tests and simple readings.
 */
class StringReading(val value: String) : SensorReading

/**
 * Abstracts a sensor input, meant to be implemented to define different types of inputs.
 */
interface SensorInput
/**
 * converts a project-defined higher-level input to a raw input, suitable for sending over a portal.
 */
typealias InputEncoder = (encodedReading: SensorInput) -> String

class StringInput(val value: String) : SensorInput
class IntInput(val value: Int) : SensorInput

fun String.toInput() = StringInput(this)
fun Int.toInput() = IntInput(this)