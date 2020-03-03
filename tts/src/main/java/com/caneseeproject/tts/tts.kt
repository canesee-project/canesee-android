package com.caneseeproject.tts

import android.speech.tts.TextToSpeech
import com.caneseeaproject.computervision.*
import com.caneseeproject.obstacledetection.*
import com.caneseeproject.sensorPortals.*

interface TextToSpeechInterface {
    fun notify(recent: NotificationType)
}

class TextToSpeachClass : TextToSpeechInterface {
    var tts: TextToSpeech? = null
    //    tts fun
    private fun speakOut(text: String) {
        //tts function implementation
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null)
    }

    override fun notify(recent: NotificationType) {
        when (recent) {
            is NotificationType.SensorNotification -> when (recent.sensorType) {
                is Vision.OCR -> speakOut("OCR is Activated now")
                is Vision.Facial -> speakOut("face recognition is turned on")
                is Vision.Emotion -> speakOut("emotion recognition is turned on")
                is Vision.ObjectDetection -> speakOut("object detection is turned on")
                is Vision.Scenery -> speakOut("scene Description")
                is ODReading.ObstacleDistance -> speakOut("Distance Detection is turned on")

            }
            is NotificationType.SensorContent -> when (recent.textReading) {
                is Vision.OCR -> speakOut(recent.textReading.transcript)
                is Vision.Facial -> speakOut(recent.textReading.prettyFace)
                is Vision.Emotion -> speakOut(recent.textReading.emotion)
                is Vision.ObjectDetection -> speakOut(recent.textReading.objects.toString())
                is Vision.Scenery -> speakOut(recent.textReading.scene)
                is ODReading.ObstacleDistance -> speakOut(recent.textReading.distance.toString())
            }
            is NotificationType.AppMessage -> when{

            }
        }
    }
}
sealed class NotificationType{
    class AppMessage :NotificationType()
    class SensorNotification(val sensorType :SensorReading) :NotificationType()
    class SensorContent(val textReading :SensorReading) :NotificationType()
}
