package com.caneseeproject.tts

import com.caneseeproject.sensorPortals.SensorInput
import com.caneseeproject.sensorPortals.SensorReading

/**
 *@author mhashim6 on 3/5/20
 */


fun SensorReading.toWhisper() = Whisper.ReadingWhisper(this)

fun SensorInput.toWhisper() = Whisper.ControlWhisper(this)

fun String.toWhisper() = Whisper.AppWhisper(this)