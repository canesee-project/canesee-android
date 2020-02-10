package com.caneseeproject.tts

import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.*
import com.caneseeproject.sensorPortals.SensorReading

interface TexrToSpeechInterface {
    fun onInit(status: Int)
    fun speakOut(text: String)
}

class TextToSpeachClass : TexrToSpeechInterface {
    var ttst: TextToSpeech? = null
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = ttst!!.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS","The Language specified is not supported!")
            }
        } else {
            Log.e("TTS", "Initilization Failed!")
        }
    }
    override fun speakOut(text: String) {
        //tts function implementation
        val textString = text.toString()
        ttst!!.speak(textString, TextToSpeech.QUEUE_FLUSH, null);
    }

    fun notify(RecentNotification: NotificationsType) =
        when (RecentNotification) {
            is NotificationsType.AppMessage -> speakOut("connection is established")
            is NotificationsType.SensorControl -> speakOut("OCR is activated")
            is NotificationsType.SensorOutput -> println("bla bla bla .....")
        }
}

sealed class NotificationsType{
    class AppMessage : NotificationsType()
    class SensorControl : NotificationsType()
    class SensorOutput (val textualResult: SensorReading) : NotificationsType()
}