package com.caneseeproject.tts

import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.*

fun main(args: Array<String>){
    var myVariable = texttospeachInstance()
    myVariable.speakOut(25469)
}

interface texttospeach {
    fun onInit(status: Int)
    fun speakOut(text: Int)
   // fun onDestroy()

}

class texttospeachInstance : texttospeach {
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
    override fun speakOut(text: Int) {
        //tts function implementation
        val textString = text.toString()
        tts!!.speak(textString, TextToSpeech.QUEUE_FLUSH, null);
    }
    /* override fun onDestroy() {
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
    }*/
}