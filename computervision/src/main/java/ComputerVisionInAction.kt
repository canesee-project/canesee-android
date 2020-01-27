package com.caneseeaproject.obstacledetection



import com.caneseeaproject.computervision.ComputerVision
import com.caneseeaproject.computervision.CVInput
import com.caneseeaproject.computervision.CVReading
import com.caneseeproject.sensorPortals.Sensor
import com.caneseeproject.sensorPortals.SensorPortal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance


class ComputerVisionInAction : ComputerVision {

    private val CVPortal: SensorPortal = TODO()
    private var CV: Sensor


    fun activate() {
        CV = CVPortal.connect()
    }

    private fun cvTokenize(rawData: String): CVReading {
        return when {
            rawData.startsWith('1') -> CVReading.ModeChange(rawData[2].toInt())
            else -> throw Exception("possibly corrupt reading bs in CV.")
        }
    }

    private fun cvEncode(processed: CVInput): String {
        return when (processed) {
            is CVInput.Vision -> {
                "${processed.rawData}"
            }
            else -> throw Exception("the russians did it for CV !")
        }
    }

    override fun modeChanges(): Flow<CVReading.ModeChange> {
        return CV
            .readings(::cvTokenize).filterIsInstance()


    }


    override suspend fun setMode(mode: CVInput) {
        CV.send(::cvEncode, mode)

    }
}


