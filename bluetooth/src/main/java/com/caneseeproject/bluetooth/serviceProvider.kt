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
import android.widget.Toast


import com.caneseeproject.sensorPortals.Sensor
import com.caneseeproject.sensorPortals.SensorPortal
import java.io.IOException
import java.util.*


 class serviceProvider ()  {

    /* val filter = IntentFilter(BluetoothDevice.ACTION_ACL_CONNECTED)

     val receiver = object : BroadcastReceiver() {
         override fun onReceive(context: Context, intent: Intent) {
             val action: String = intent.action
             when(action) {
                 BluetoothDevice.ACTION_ACL_CONNECTED -> {
                     Toast.makeText(context, "CCCOONNNEECCTTEEEDD", Toast.LENGTH_LONG)
                 }

                 }

         }

     }
     init {
         context.registerReceiver(receiver, filter)
     }*/
      class ConnectionThreed(device: BluetoothDevice) : Thread(){


        val MY_UUID = UUID.fromString("e7f78a05-42f8-4f5d-8886-d57efb14dcef")
        val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
        private val mmSocket: BluetoothSocket? by lazy(LazyThreadSafetyMode.NONE) {
            device.createRfcommSocketToServiceRecord(MY_UUID)

        }

        public override fun run() {
            // Cancel discovery because it otherwise slows down the connection.
            bluetoothAdapter?.cancelDiscovery()

            mmSocket?.use { socket ->
                // Connect to the remote device through the socket. This call blocks
                // until it succeeds or throws an exception.
                socket.connect()
                Log.i("MYACTIVITY", "WEEE AARREEE CCOONNEECCTTEEDD")

                // The connection attempt succeeded. Perform work associated with
                // the connection in a separate thread.
                //manageMyConnectedSocket(socket)
            }

        }

        // Closes the client socket and causes the thread to finish.
        fun cancel() {
            try {
                mmSocket?.close()
            } catch (e: IOException) {
                Log.e(TAG, "Could not close the client socket", e)

            }
        }



    }








 }