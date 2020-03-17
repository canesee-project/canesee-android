package com.caneseeproject.sensorPortals

/**
 *@author mhashim6 on 2020-01-14
 */


/**
 * Abstracts a sensor reading, meant to be implemented to define different types of readings.
 */
interface SensorReading

/**
 * Dummy SensorReading for tests and simple readings.
 */
class StringReading(val value: String) : SensorReading

/**
 * Abstracts a sensor input, meant to be implemented to define different types of inputs.
 */
interface SensorControl

class StringControl(val value: String) : SensorControl
class IntControl(val value: Int) : SensorControl

fun String.toControl() = StringControl(this)
fun Int.toControl() = IntControl(this)