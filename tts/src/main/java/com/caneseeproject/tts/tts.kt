package com.caneseeproject.tts

import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.*
import com.caneseeproject.sensorPortals.SensorReading
import com.caneseeproject.sensorPortals.SensorInput
import com.caneseeproject.obstacledetection.*
import com.caneseeaproject.computervision.*


interface TextToSpeechInterface {
    fun speakOut(text: String)
}

class TextToSpeachClass : TextToSpeechInterface {
    var tts: TextToSpeech? = null
    //    tts fun
    override fun speakOut(text: String) {
        //tts function implementation
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    fun notify(
        input: Device,
        recent: NotificationType,
        glassesMode: Vision,
        obstacleMode: ODReading
    ) {
        when (input) {

//              Glasses
            Device.Glasses -> when (recent) {
                is NotificationType.SensorNotification -> when (glassesMode) {
                    is Vision.OCR -> speakOut("OCR is activated")
                    is Vision.Facial -> speakOut("face recognition is turned on")
                    is Vision.Emotion -> speakOut("Emotion recognition is turned on")
                    is Vision.ObjectDetection -> speakOut("Object detection is turned on")
                    is Vision.Scenery -> speakOut("Scene Description is turned on ")
                }
                is NotificationType.SensorContent -> when (glassesMode) {
                    is Vision.OCR -> speakOut(glassesMode.transcript)
                    is Vision.Facial -> speakOut(glassesMode.prettyFace)
                    is Vision.Emotion -> speakOut(glassesMode.emotion)
                    is Vision.ObjectDetection -> speakOut(glassesMode.objects.toString())
                    is Vision.Scenery -> speakOut(glassesMode.scene)
                }
                is NotificationType.AppMessage -> speakOut("glasses connection is established")
            }

//              Cane
            Device.Cane -> when (recent) {
                is NotificationType.SensorNotification -> when (obstacleMode) {
                    is ODReading.ObstacleDistance -> speakOut("Cane Distance is turned on")
                }
                is NotificationType.SensorContent -> when (obstacleMode) {
                    is ODReading.ObstacleDistance -> speakOut(obstacleMode.distance.toString())

                }
                is NotificationType.AppMessage -> speakOut("Cane connection is established")
            }
        }
    }
}
sealed class NotificationType{

    class AppMessage :NotificationType()
    class SensorNotification(val textInput :SensorInput) :NotificationType()
    class SensorContent(val textReading :SensorReading) :NotificationType()
}
enum class Device {
    Glasses,
    Cane
}