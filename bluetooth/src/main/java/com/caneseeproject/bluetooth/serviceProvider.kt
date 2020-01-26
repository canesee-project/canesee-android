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


 class ServiceProvider(): SensorPortal  {
     val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
     val mydevice : String = "98:D3:61:FD:66:FB"
     var device = bluetoothAdapter!!.getRemoteDevice(mydevice)

     override fun connect(bluetoothDevice: BluetoothDevice, flag : Boolean) :Sensor {
         val sensordata = sensorData()

         val MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
         val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
         val socket: BluetoothSocket = device.createRfcommSocketToServiceRecord(MY_UUID)
         bluetoothAdapter!!.cancelDiscovery()
         if(flag==true){
             socket.connect()
             Log.i("MYACTIVITY", "we are connected")
            }

         if(flag == false){
             socket.close()
             Log.i("MYACTIVITY", " socket is closed")
            }

         return sensordata

        }
    fun startBluetooth(){
        if (bluetoothAdapter?.isEnabled == false) {
            bluetoothAdapter.enable()
            Log.i("MYACTIVITY", "on")

        }
    }
    fun endBluetooth(){
        if (bluetoothAdapter?.isEnabled == true) {
            bluetoothAdapter.disable()
            Log.i("MYACTIVITY", "of")

        }
    }
 }