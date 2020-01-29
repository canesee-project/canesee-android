package com.caneseeproject.bluetooth

import android.bluetooth.BluetoothSocket
import com.caneseeproject.sensorPortals.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import java.io.InputStream
import java.io.OutputStreamWriter

internal class SensorData(private val socket: BluetoothSocket) : Sensor {
    private val mmInStream: InputStream = socket.inputStream
    private val mmOutStream: OutputStreamWriter = socket.outputStream.writer()

    override fun <T : SensorReading> readings(tokenize: ReadingTokenizer<T>): Flow<T> =
        mmInStream.reader().buffered(5).lineSequence().map(tokenize).asFlow()

    override suspend fun <T : SensorInput> send(encode: InputEncoder<T>, vararg messages: T) {
        messages.forEach {
            mmOutStream.run { write("${encode(it)}\n"); flush() }
        }
    }

    override val isActive: Boolean
        get() = socket.isConnected

    override fun shutdown() {
        socket.close()
    }

}

