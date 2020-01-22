package com.caneseeproject.bluetooth

import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter



class discover {
    val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
    private var discoveredDevices:MutableList<BluetoothDevice> = mutableListOf()

        val receiver = object : BroadcastReceiver() {

            override fun onReceive(context: Context, intent: Intent) {
                val action: String = intent.action
                when(action) {
                    BluetoothDevice.ACTION_FOUND -> {
                        // Discovery has found a device. Get the BluetoothDevice
                        // object and its info from the Intent.
                        val device: BluetoothDevice =
                            intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                        val deviceName = device.name
                        val deviceHardwareAddress = device.address // MAC address
                    }
                }
            }
        }
    fun registerreceiver(){
        globalActivity.ctx?.registerReceiver(receiver, filter)

    }
    fun canceldicovery(){
        globalActivity.ctx?.unregisterReceiver(this.receiver)
    }






}