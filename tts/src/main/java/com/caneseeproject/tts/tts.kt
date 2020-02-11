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
    var tts: TextToSpeech? = null
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts!!.setLanguage(Locale.US)

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
        tts!!.speak(textString, TextToSpeech.QUEUE_FLUSH, null);
    }

    fun notify(RecentNotification: NotificationType) =
        when (RecentNotification) {
            is NotificationType.AppMessage -> speakOut("connection is established")
            is NotificationType.SensorControl -> speakOut("OCR is activated")
            is NotificationType.SensorOutput -> println("bla bla bla .....")
        }
}

sealed class NotificationType{
    class AppMessage : NotificationType()
    class SensorControl : NotificationType()
    class SensorOutput (val textualResult: SensorReading) : NotificationType()
}