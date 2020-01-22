package com.caneseeproject.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.content.BroadcastReceiver
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log


import com.caneseeproject.sensorPortals.Sensor
import com.caneseeproject.sensorPortals.SensorPortal
import java.io.IOException
import java.util.*


 class serviceProvider  {
    val REQUEST_ENABLE_BT  = 2
    //val deviceHardwareAddress = 1234
    val NAME = "myapp"
    val MY_UUID = UUID.fromString("e7f78a05-42f8-4f5d-8886-d57efb14dcef")
     val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)

    val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()


         fun disconnect(){
             if (bluetoothAdapter?.isEnabled == true) {
                 bluetoothAdapter.disable()

             }

         }
         fun Bonnect(){
            if (bluetoothAdapter?.isEnabled == false) {
                bluetoothAdapter.enable()
                Log.i("MyActivity", "ooooooonnnnnnnnnnn")
            }



        }
}