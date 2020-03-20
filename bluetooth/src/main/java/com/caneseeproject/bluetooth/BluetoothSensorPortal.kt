package com.caneseeproject.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import com.caneseeproject.sensorPortals.SensorPortal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import java.io.InputStream
import java.io.OutputStreamWriter
import java.util.*


internal class BluetoothSensorPortal(private val MAC: String ) : SensorPortal {
    private val bluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    // val HC05 : String = "98:D3:61:FD:66:FB"
    private var device: BluetoothDevice = bluetoothAdapter.getRemoteDevice(MAC)

    private lateinit var socket: BluetoothSocket
    private val mmInStream: InputStream by lazy { socket.inputStream }
    private val mmOutStream: OutputStreamWriter by lazy { socket.outputStream.writer() }

    override fun open() {
        socket = device.createRfcommSocketToServiceRecord(SERIAL_UUID)
        bluetoothAdapter.cancelDiscovery()
        socket.connect()
    }
  
    override fun receive(): Flow<String> =
        mmInStream.reader().buffered(1024).lineSequence().filterNotNull().asFlow()

    override suspend fun send(vararg messages: String) {
        mmOutStream.run {
            messages.forEach { write("$it\n") }
            flush()
        }
    }
    
    override fun shutdown() {
        socket.close()
    }

    override val isActive: Boolean
        get() = socket.isConnected

    companion object {
        private val SERIAL_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    }
}

fun BluetoothPortal(MAC: String): SensorPortal = BluetoothSensorPortal(MAC)
