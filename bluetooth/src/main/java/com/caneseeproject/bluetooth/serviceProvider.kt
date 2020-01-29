package com.caneseeproject.bluetooth
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.hardware.SensorDirectChannel
import android.util.Log
import com.caneseeproject.sensorPortals.Sensor
import com.caneseeproject.sensorPortals.SensorPortal
import java.io.IOException
import java.util.*


 class BluetoothSensorPortal(private val MAC : String): SensorPortal  {
     val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
    // val HC05 : String = "98:D3:61:FD:66:FB"
     var device = bluetoothAdapter!!.getRemoteDevice(MAC)
     override fun connect() :Sensor {
         val MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
         val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
         val socket: BluetoothSocket = device.createRfcommSocketToServiceRecord(MY_UUID)
         val sensordata = sensorData(socket)
         bluetoothAdapter!!.cancelDiscovery()
         socket.connect()
         Log.i("MYACTIVITY", "we are connected")
         return sensordata
        }
 }