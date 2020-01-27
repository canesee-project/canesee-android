package com.caneseeaproject.obstacledetection


import com.caneseeaproject.computervision.InterCV
import com.caneseeaproject.computervision.cvInput
import com.caneseeaproject.computervision.cvReading
import com.caneseeproject.sensorPortals.Sensor
import com.caneseeproject.sensorPortals.SensorPortal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance


class ApplyInterCV : InterCV {

    private val CVPortal: SensorPortal = TODO()
    private var CV: Sensor


    fun activate() {
        CV = CVPortal.connect()
    }

    private fun cvTokenize(rawData: String): cvReading {
        return when {
            rawData.startsWith('1') -> cvReading.getMode(rawData[2].toInt())
            else -> throw Exception("possibly corrupt reading bs in CV.")
        }
    }

     private fun cvEncode( processed: cvInput): String {
        return when (processed) {
            is cvInput.getData -> {"${processed.rawData}"}
            else -> throw Exception("the russians did it for CV !")
        }
    }

    override fun sendData():Flow<cvReading.getMode> {
        return  CV
            .readings(::cvTokenize).filterIsInstance()


    }


    override suspend fun setMode(mode: cvInput) {
           // CV.send(::cvEncode , mode)

        }
    }


