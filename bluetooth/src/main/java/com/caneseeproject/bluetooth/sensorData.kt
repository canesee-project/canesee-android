package com.caneseeproject.bluetooth

import android.bluetooth.BluetoothSocket
import com.caneseeproject.sensorPortals.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.forEach
import java.io.InputStream
import java.io.OutputStream
import java.nio.charset.Charset

class sensorData(private  val socket: BluetoothSocket) : Sensor{
    val mmInStream: InputStream = socket.inputStream
    val mmOutStream: OutputStream = socket.outputStream

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

