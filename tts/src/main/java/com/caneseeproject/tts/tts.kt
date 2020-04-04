package com.caneseeproject.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import com.caneseeaproject.computervision.*
import com.caneseeproject.obstacledetection.ODControl
import com.caneseeproject.obstacledetection.ODReading
import com.caneseeproject.sensorPortals.SensorControl
import com.caneseeproject.sensorPortals.SensorReading


interface CaneSeeVoice {
    fun start(onReady: (status: Int) -> Unit)
    fun whisper(secret: Whisper)

    companion object Factory {
        fun create(context: Context): CaneSeeVoice = CaneSeeVoiceImpl(context)
    }
}

internal class CaneSeeVoiceImpl(
    private val context: Context
) : CaneSeeVoice {

    private lateinit var tts: TextToSpeech
    override fun start(onReady: (status: Int) -> Unit) {
        tts = TextToSpeech(context, onReady)
    }

    private fun speakOut(text: String) {
        tts.speak(text, TextToSpeech.QUEUE_ADD, null)
    }

    override fun whisper(secret: Whisper) {
        speakOut(
            when (secret) {
                is Whisper.ControlWhisper -> when (secret.control) {
                    is CVControl.ModeChange -> when (secret.control.mode) {
                        OCR -> "تم تفعيلُ وضعِ قراءةِ النصوص"
                        SCENES -> "تم تفعيلُ وَصف المَشاهد"
                        PRETTY_FACES -> "تم تفعيلُ التعرف على الأشخاص"
                        EMOTIONS -> "تم تفعيلُ وضعِ التعرفِ على تعابير الوجه"
                        OBJECTS -> "تم تفعيلُ وضعِ التعرفِ على الأشياء"
                        else -> throw Exception("TTS: This is bad.")
                    }
                    is ODControl.RangeControl -> "Distance Detection is turned on" //TODO
                    else -> throw Exception("TTS: This is really bad.")
                }

                is Whisper.ReadingWhisper -> when (secret.reading) {
                    is Vision.OCR -> secret.reading.transcript
                    is Vision.Facial -> secret.reading.prettyFace
                    is Vision.Emotion -> secret.reading.emotion
                    is Vision.ObjectDetection ->
                        secret.reading.objects.map { "${it.lbl} ${it.pos}" }.toString() //TODO
                    is Vision.Scenery -> secret.reading.scene
                    is ODReading.ObstacleDistance -> secret.reading.distance.toString()
                    else -> throw Exception("TTS: Oh come on!")
                }

                is Whisper.AppWhisper -> secret.msg
            }
        )
    }
}

sealed class Whisper {
    class AppWhisper(val msg: String) : Whisper()
    class ControlWhisper(val control: SensorControl) : Whisper()
    class ReadingWhisper(val reading: SensorReading) : Whisper()
}
