package com.caneseeproject.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import com.caneseeproject.sensorPortals.Sensor
import com.caneseeproject.sensorPortals.SensorPortal
import java.util.*


internal class BluetoothSensorPortal(private val MAC: String) : SensorPortal {

    private val bluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    // val HC05 : String = "98:D3:61:FD:66:FB"
    private var device: BluetoothDevice = bluetoothAdapter.getRemoteDevice(MAC)

    override fun connect(): Sensor {
        val socket: BluetoothSocket = device.createRfcommSocketToServiceRecord(SERIAL_UUID)
        val sensordata = SensorData(socket)
        bluetoothAdapter.cancelDiscovery()
        socket.connect()
        return sensordata
    }

    companion object {
        private val SERIAL_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    }
}

fun BluetoothPortal(MAC: String): SensorPortal = BluetoothSensorPortal(MAC)
