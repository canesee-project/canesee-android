package com.caneseeproject.bluetooth

import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.text.util.Rfc822Tokenizer
import android.text.util.Rfc822Tokenizer.tokenize
import com.caneseeproject.sensorPortals.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.io.InputStream
import java.io.OutputStream

class sensorData(private  val socket: BluetoothSocket) : Sensor{
    val mmInStream: InputStream = socket.inputStream
    val mmBuffer: ByteArray = ByteArray(1024)

    override fun <T : SensorReading> readings(tokenize: ReadingTokenizer<T>): Flow<T> {
        var sringed_flow :ReceiveChannel<Char> = Channel()
        var reciever : Channel<String> = Channel()
        fun producer() = runBlocking {
            sringed_flow = produce {
                mmInStream.read(mmBuffer).toString().forEach {
                    send(it)
                    reciever.consumeEach { it }
                }
            }

        }
        val result: Flow<SensorReading> = reciever.consumeAsFlow().map { tokenize(it) }
        return result


    }






    override suspend fun <T : SensorInput> send(encode: InputEncoder<T>, vararg messages: T) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override val isActive: Boolean
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun shutdown() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

