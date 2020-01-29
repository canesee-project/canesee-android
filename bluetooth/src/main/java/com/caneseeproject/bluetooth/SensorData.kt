package com.caneseeproject.bluetooth

import android.bluetooth.BluetoothSocket
import com.caneseeproject.sensorPortals.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import java.io.InputStream
import java.io.OutputStream

class SensorData(private val socket: BluetoothSocket) : Sensor {
    private val mmInStream: InputStream = socket.inputStream
    private val mmOutStream: OutputStream = socket.outputStream

    override fun <T : SensorReading> readings(tokenize: ReadingTokenizer<T>): Flow<T> =
        mmInStream.reader().buffered(5).lineSequence().map(tokenize).asFlow()

    override suspend fun <T : SensorInput> send(encode: InputEncoder<T>, vararg messages: T) =
        messages.forEach { mmOutStream.write(encode(it).toByteArray()) }

    override val isActive: Boolean
        get() = socket.isConnected

    override fun shutdown() {
        socket.close()
    }

}

