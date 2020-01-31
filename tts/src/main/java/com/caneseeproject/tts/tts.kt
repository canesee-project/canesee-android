package com.caneseeproject.tts

import android.speech.tts.TextToSpeech
import java.util.*

fun main(args: Array<String>){
    var myVariable = texttospeachInstance()
    myVariable.speakOut(25469)
}

interface texttospeach {
    var tts: TextToSpeech? = null
    fun speakOut(text: Int )
   // fun onDestroy()
}

class texttospeachInstance : texttospeach {

    override fun speakOut(text: Int) {
        //tts function implementation
        val textString = text.toString()
        tts!!.speak(textString, TextToSpeech.QUEUE_FLUSH, null);
    }
    /*override fun onDestroy() {
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
    }*/
}